package yerong.wedle.chat.domain.chatting;

import jakarta.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "chat_message")
public class ChatMessage implements Serializable {
    @Id
    private String id;
    @Indexed
    private Long chatRoomId;
    private Long senderId;
    private String content;

    private LocalDateTime createdAt;
    private int readCount;

    private ChatType chatType;

    private String imageName;
    private String imageUrl;
}