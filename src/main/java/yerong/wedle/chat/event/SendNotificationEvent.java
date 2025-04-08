package yerong.wedle.chat.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import yerong.wedle.chat.domain.notification.Notification;

@Getter
public class SendNotificationEvent extends ApplicationEvent {

    private final Long chatRoomId;
    private final Notification notification;

    public SendNotificationEvent(Object source, Long chatRoomId, Notification notification) {
        super(source);
        this.chatRoomId = chatRoomId;
        this.notification = notification;
    }
}
