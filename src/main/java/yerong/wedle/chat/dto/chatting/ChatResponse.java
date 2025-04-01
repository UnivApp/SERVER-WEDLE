package yerong.wedle.chat.dto.chatting;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Slice;
import yerong.wedle.chat.domain.chatting.ChatMessage;
import yerong.wedle.chat.domain.chatting.ChatRoom;
import yerong.wedle.chat.domain.chatting.ChatType;
import yerong.wedle.member.domain.Member;

public class ChatResponse {

    @Data
    @AllArgsConstructor
    public static class CreateNewChatRoomResponse {
        private boolean isNew;
        private Long chatRoomId;
    }

    @Data
    @AllArgsConstructor
    public static class ChatRoomsResponse {
        private String memberName;
        private List<ChatRoomResponse> chatRooms;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class ChatRoomResponse {
        private Long chatRoomId;
        private String chatRoomTitle;
        private String otherUserProfileImage;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createdChatRoomDate;
        private String lastMessageContent;
        private long unReadMessageCount;
        @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime lastMessageTime;

        public ChatRoomResponse(Long chatRoomId, String chatRoomTitle, String otherUserProfileImage,
                                LocalDateTime createdChatRoomDate) {
            this.chatRoomId = chatRoomId;
            this.chatRoomTitle = chatRoomTitle;
            this.otherUserProfileImage = otherUserProfileImage;
            this.createdChatRoomDate = createdChatRoomDate;
        }

        public ChatRoomResponse(ChatRoom chatRoom, Member otherMember) {
            this.chatRoomId = chatRoom.getId();
            this.otherUserProfileImage =
                    otherMember.getProfileImageUrl() != null ? otherMember.getProfileImageUrl() : null;
            this.createdChatRoomDate = chatRoom.getCreatedAt();
        }

        public void changeLastMessage(String lastMessageContent, LocalDateTime lastMessageTime) {
            this.lastMessageContent = lastMessageContent;
            this.lastMessageTime = lastMessageTime;
        }

        public void changeUnReadMessageCount(long unReadMessageCount) {
            this.unReadMessageCount = unReadMessageCount;
        }
    }

    @Data
    @AllArgsConstructor
    public static class ChatMessageListResponse {
        private Long memberId;
        private Long chatRoomId;
        private Slice<ChatMessageResponse> chatMessages;
    }

    @Data
    @AllArgsConstructor
    public static class ChatMessageResponse {
        private String chatMessageId;
        private Long senderId;
        private boolean myMsg;
        private String content;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;
        private int readCount;
        private ChatType chatType;
        private String imageUrl;

        public ChatMessageResponse(ChatMessage chatMessage, Long memberId) {
            this.chatMessageId = chatMessage.getId();
            this.senderId = chatMessage.getSenderId();
            this.myMsg = chatMessage.getSenderId().equals(memberId);
            this.content = chatMessage.getContent();
            this.createdAt = chatMessage.getCreatedAt();
            this.readCount = chatMessage.getReadCount();
            this.chatType = chatMessage.getChatType();
            this.imageUrl = chatMessage.getImageUrl();
        }
    }
}
