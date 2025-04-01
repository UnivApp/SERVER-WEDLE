package yerong.wedle.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import yerong.wedle.chat.domain.chatting.Message;
import yerong.wedle.chat.domain.notification.Notification;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaSender {
    private final KafkaTemplate<String, Message> kafkaChatTemplate;
    private final KafkaTemplate<String, Notification> kafkaNotificationTemplate;

    public void sendMessage(String topic, String chatRoomId, Message message) {
        kafkaChatTemplate.send(topic, chatRoomId, message);
        log.debug("메시지 전송 완료: topic={}, id={}, message={}", topic, chatRoomId, message);
    }

    public void sendNotification(String topic, String chatRoomId, Notification notification) {
        kafkaNotificationTemplate.send(topic, chatRoomId, notification);
        log.debug("메시지 전송 완료: topic={}, id={}, notification={}", topic, chatRoomId, notification);
    }
}
