package yerong.wedle.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import yerong.wedle.chat.domain.chatting.Message;
import yerong.wedle.chat.domain.notification.Notification;
import yerong.wedle.chat.util.KafkaVO;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaReceiver {

    private final SimpMessageSendingOperations template;

    @KafkaListener(topics = KafkaVO.KAFKA_CHAT_TOPIC, containerFactory = "kafkaListenerContainerFactory")
    public void receiveChatMessage(Message message) {
        String destination = "/sub/chat/room/" + message.getChatRoomId();
        log.debug("채팅 메시지 전송 위치 = {}", destination);
        log.debug("채팅 방으로 메시지 전송 = {}", message);

        template.convertAndSend(destination, message);
    }

    @KafkaListener(topics = KafkaVO.KAFKA_NOTIFICATION_TOPIC, containerFactory = "kafkaListenerContainerFactory2")
    public void receiveNotificationMessage(Notification notification) {
        String destination = "/sub/notification/" + notification.getRecipientId();
        log.debug("알림 메시지 전송 위치 = {}", destination);
        log.debug("알림 메시지 전송 = {}", notification);

        template.convertAndSend(destination, notification);
    }
}
