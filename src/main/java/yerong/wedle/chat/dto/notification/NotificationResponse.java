package yerong.wedle.chat.dto.notification;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import yerong.wedle.chat.domain.notification.ChatNotification;

public class NotificationResponse {
    @Data
    @AllArgsConstructor
    public static class ChatNotificationResponse {
        private Long chatNotificationId;
        private Long chatRoomId;
        private String senderNick;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createdDate;
        private String content;
        private boolean hasRead;

        public ChatNotificationResponse(ChatNotification chatNotification) {
            this.chatNotificationId = chatNotification.getId();
            this.chatRoomId = chatNotification.getChatRoomId();
            this.senderNick = chatNotification.getSender().getNickname();
            this.createdDate = chatNotification.getCreatedAt();
            this.content = chatNotification.getContent();
            this.hasRead = chatNotification.getHasRead();
        }
    }
}
