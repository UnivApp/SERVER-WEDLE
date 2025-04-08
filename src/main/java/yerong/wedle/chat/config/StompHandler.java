package yerong.wedle.chat.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import yerong.wedle.chat.domain.notification.Notification;
import yerong.wedle.chat.repository.ChatRoomRepository;
import yerong.wedle.chat.service.ChatService;
import yerong.wedle.chat.service.KafkaSender;
import yerong.wedle.chat.service.NotificationService;
import yerong.wedle.chat.util.ChatUtil;
import yerong.wedle.chat.util.KafkaVO;

@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@Component
@RequiredArgsConstructor
@Slf4j
public class StompHandler implements ChannelInterceptor {
    private final ChatService chatService;
    private final NotificationService notificationService;
    private final ChatRoomRepository chatRoomRepository;
    private final KafkaSender kafkaSender;

    private static final String TOPIC_NOTIFICATION = "/sub/notification";

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand stompCommand = accessor.getCommand();

        log.info("command = {}", stompCommand);
        log.info("destination = {}", accessor.getDestination());
        log.info("message = {}", accessor.getMessage());

        if (stompCommand != null) {
            handleStompCommand(stompCommand, accessor);
        }
        return ChannelInterceptor.super.preSend(message, channel);
    }

    private void handleStompCommand(StompCommand stompCommand, StompHeaderAccessor accessor) {
        switch (stompCommand) {
            case CONNECT:
                log.debug("CONNECT");
                break;
            case SUBSCRIBE:
                handleSubscribe(accessor);
                break;
            case UNSUBSCRIBE:
                handleUnsubscribe(accessor);
                break;
            case SEND:
                log.debug("SEND");
                break;
            case DISCONNECT:
                handleDisconnect(accessor);
                break;
            case ERROR:
                log.debug("WebSocket Error!!");
                break;
        }
    }

    private void handleSubscribe(StompHeaderAccessor accessor) {
        if (accessor.getDestination().startsWith(TOPIC_NOTIFICATION)) {
            log.debug("알림 SUBSCRIBE");
            return;
        }

        handleChatRoomSubscription(accessor);
    }

    private void handleChatRoomSubscription(StompHeaderAccessor accessor) {
        log.debug("채팅방 SUBSCRIBE");

        Long chatRoomId = getChatRoomId(accessor);
        Long memberId = getMemberId(accessor);

        validateChatRoomParticipant(chatRoomId, memberId);

        updateSubscription(accessor, chatRoomId, memberId);

        chatService.getOtherMemberIdByChatRoomId(chatRoomId)
                .ifPresent(otherMemberId -> notifyReadCountUpdate(chatRoomId, otherMemberId));

        notificationService.deleteAllNotificationsInChatRoomByMember(memberId, chatRoomId);
    }

    private void updateSubscription(StompHeaderAccessor accessor, Long chatRoomId, Long memberId) {
        deleteExistingSubscription(accessor);
        chatService.deleteChatRoomParticipantFromRedis();
        accessor.getSessionAttributes().put(ChatUtil.SUBSCRIPTIONS, chatRoomId);
        chatService.saveChatRoomParticipantToRedis(chatRoomId);
        chatService.updateUnreadMessages(chatRoomId);
    }

    private void deleteExistingSubscription(StompHeaderAccessor accessor) {
        Long subscriptions = (Long) accessor.getSessionAttributes().get(ChatUtil.SUBSCRIPTIONS);
        if (subscriptions != null) {
            accessor.getSessionAttributes().remove(ChatUtil.SUBSCRIPTIONS);
        }
    }

    private void notifyReadCountUpdate(Long chatRoomId, Long otherMemberId) {
        log.debug("상대방에게 readCount값 갱신 알림 전송");
        Notification readCountUpdateNotification = Notification.createReadCountUpdateNotification(chatRoomId,
                otherMemberId);
        kafkaSender.sendNotification(KafkaVO.KAFKA_NOTIFICATION_TOPIC, String.valueOf(chatRoomId),
                readCountUpdateNotification);
    }

    private void handleUnsubscribe(StompHeaderAccessor accessor) {
        String destination = accessor.getDestination();
        if (destination != null && accessor.getDestination().startsWith(TOPIC_NOTIFICATION)) {
            log.debug("알림 UNSUBSCRIBE");
            return;
        }
        handleChatRoomUnsubscription(accessor);
    }

    private void handleChatRoomUnsubscription(StompHeaderAccessor accessor) {
        log.debug("채팅방 UNSUBSCRIBE");
        deleteExistingSubscription(accessor);
        chatService.deleteChatRoomParticipantFromRedis();
    }

    private void handleDisconnect(StompHeaderAccessor accessor) {
        log.debug("웹소켓 DISCONNECT");
        handleChatRoomUnsubscription(accessor);
    }

    private void validateChatRoomParticipant(Long chatRoomId, Long memberId) {
        chatRoomRepository.findByIdAndMemberId(chatRoomId, memberId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방에 참여하지 않은 사용자는 구독할 수 없습니다"));
    }

    private Long getChatRoomId(StompHeaderAccessor accessor) {
        return Long.valueOf(accessor.getDestination().split("/")[4]);
    }

    private Long getMemberId(StompHeaderAccessor accessor) {
        return (Long) accessor.getSessionAttributes().get(ChatUtil.MEMBER_ID);
    }
}
