package yerong.wedle.chat.service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import yerong.wedle.chat.domain.chatting.ChatRoom;
import yerong.wedle.chat.domain.chatting.Message;
import yerong.wedle.chat.domain.notification.ChatNotification;
import yerong.wedle.chat.domain.notification.Notification;
import yerong.wedle.chat.domain.notification.NotificationType;
import yerong.wedle.chat.dto.notification.NotificationResponse.ChatNotificationResponse;
import yerong.wedle.chat.event.SendNotificationEvent;
import yerong.wedle.chat.repository.ChatNotificationRepository;
import yerong.wedle.chat.repository.ChatRoomRepository;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.exception.UnauthorizedAccessException;
import yerong.wedle.member.repository.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {
    private final ChatNotificationRepository chatNotificationRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final MemberRepository memberRepository;

    @Override
    public void sendNotification(Message message, Long senderId, Long chatRoomId) {
        try {
            ChatRoom findChatRoom = chatRoomRepository.findByIdWithMembers(chatRoomId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다."));

            Member recipient = getRecipientFromChatRoom(findChatRoom, senderId);
            Member sender = getSenderFromChatRoom(findChatRoom, senderId);

            chatNotificationRepository.save(
                    new ChatNotification(sender, recipient.getMemberId(), chatRoomId, message.getContent()));

            Notification notification = new Notification(chatRoomId, recipient.getMemberId(), sender.getNickname(),
                    sender.getProfileImageUrl() != null ? sender.getProfileImageUrl() : null,
                    LocalDateTime.now(), message.getContent(), NotificationType.CHAT);

            applicationEventPublisher.publishEvent(
                    new SendNotificationEvent(this, chatRoomId, notification));
        } catch (Exception e) {
            log.error("메시지 알림 전송 에러");
        }
    }

    private Member getRecipientFromChatRoom(ChatRoom findChatRoom, Long senderId) {
        return findChatRoom.getSender().getMemberId().equals(senderId) ? findChatRoom.getRecipient()
                : findChatRoom.getSender();
    }

    private Member getSenderFromChatRoom(ChatRoom findChatRoom, Long senderId) {
        return findChatRoom.getSender().getMemberId().equals(senderId) ? findChatRoom.getSender()
                : findChatRoom.getRecipient();
    }

    @Override
    @Transactional
    public Slice<ChatNotificationResponse> getChatNotificationsByMemberId(Long cursorId,
                                                                          Pageable pageable) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);
        Slice<ChatNotification> sliceChatNotifications
                = chatNotificationRepository.findSliceChatNotificationsBySender(member, cursorId, pageable);

        List<ChatNotification> notifications = sliceChatNotifications.getContent();

        List<ChatNotificationResponse> notificationDtos = mapToDto(notifications);

        markNotificationsAsRead(notifications);

        return new SliceImpl<>(notificationDtos, pageable, sliceChatNotifications.hasNext());
    }

    private List<ChatNotificationResponse> mapToDto(List<ChatNotification> chatNotifications) {
        return chatNotifications.stream()
                .map(ChatNotificationResponse::new)
                .collect(Collectors.toList());
    }

    private void markNotificationsAsRead(List<ChatNotification> chatNotifications) {
        for (ChatNotification chatNotification : chatNotifications) {
            if (!chatNotification.getHasRead()) {
                chatNotification.changeHasReadToTrue();
            }
        }
    }

    @Override
    @Transactional
    public void deleteSingleChatNotification(Long chatNotificationId) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);
        Long memberId = member.getMemberId();

        int deletedCount = chatNotificationRepository.deleteByIdAndRecipientId(chatNotificationId, memberId);

        if (deletedCount == 0) {
            throw new UnauthorizedAccessException();
        }
    }

    @Override
    @Transactional
    public void deleteAllNotificationsOfMember() {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);
        Long memberId = member.getMemberId();
        chatNotificationRepository.deleteByRecipientId(memberId);
    }

    @Override
    @Transactional
    public void deleteAllNotificationsInChatRoomByMember(Long memberId, Long chatRoomId) {
        chatNotificationRepository.deleteByRecipientIdAndChatRoomId(memberId, chatRoomId);
    }

    private String getCurrentUserId() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();

        return socialId;
    }
}
