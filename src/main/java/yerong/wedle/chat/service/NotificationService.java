package yerong.wedle.chat.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import yerong.wedle.chat.domain.chatting.Message;
import yerong.wedle.chat.dto.notification.NotificationResponse.ChatNotificationResponse;

public interface NotificationService {
    void sendNotification(Message message, Long senderId, Long chatRoomId);

    Slice<ChatNotificationResponse> getChatNotificationsByMemberId(Long cursorId, Pageable pageable);

    void deleteSingleChatNotification(Long chatNotificationId);

    void deleteAllNotificationsOfMember();

    void deleteAllNotificationsInChatRoomByMember(Long memberId, Long chatRoomId);
}
