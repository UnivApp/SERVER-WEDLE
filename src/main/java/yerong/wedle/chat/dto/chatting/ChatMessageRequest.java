package yerong.wedle.chat.dto.chatting;

import lombok.Data;

@Data
public class ChatMessageRequest {
    private Long chatRoomId;
    private String message;
}
