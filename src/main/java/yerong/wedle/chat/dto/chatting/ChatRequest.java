package yerong.wedle.chat.dto.chatting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class ChatRequest {

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class CreateNewChatRoomRequest {

        private Long receiverId;
    }
}
