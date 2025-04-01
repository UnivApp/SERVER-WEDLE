package yerong.wedle.chat.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import yerong.wedle.chat.domain.chatting.Message;

@Getter
public class SendMessageEvent extends ApplicationEvent {

    private final Long chatRoomId;
    private final Message message;

    public SendMessageEvent(Object source, Long chatRoomId, Message message) {
        super(source);
        this.chatRoomId = chatRoomId;
        this.message = message;
    }
}
