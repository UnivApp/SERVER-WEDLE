package yerong.wedle.chat.domain.chatting;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {

    private Long chatRoomId;

    private Long senderId;

    private String content;

    private LocalDateTime createdAt;
    private int readCount;

    private ChatType chatType;

    private String imageName;
    private String imageUrl;

    public void prepareMessageForSending(Long senderId, LocalDateTime createdAt, int readCount) {
        this.senderId = senderId;
        this.createdAt = createdAt;
        this.readCount = readCount;
    }

    public ChatMessage convertToChatMessage() {
        return ChatMessage.builder()
                .chatRoomId(chatRoomId)
                .senderId(senderId)
                .content(content)
                .createdAt(createdAt)
                .readCount(readCount)
                .chatType(chatType)
                .imageName(imageName)
                .imageUrl(imageUrl)
                .build();
    }
}