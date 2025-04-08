package yerong.wedle.chat.kafka;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import yerong.wedle.chat.domain.chatting.Message;
import yerong.wedle.chat.domain.notification.Notification;

@EnableKafka
@Configuration
public class ProducerConfiguration {

    @Value("${kafka.bootstrap-servers}")
    private String kafkaServer;
    @Value("${kafka.producer.key-serializer}")
    private String keySerializer;

    @Value("${kafka.producer.value-serializer}")
    private String valueSerializer;

    // Message 템플릿
    @Bean
    public ProducerFactory<String, Message> messageProducerFactory() {
        return new DefaultKafkaProducerFactory<>(messageProducerConfigurations());
    }

    @Bean
    public Map<String, Object> messageProducerConfigurations() {
        return ImmutableMap.<String, Object>builder()
                .put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer)
                .put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer)
                .put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer)
                .build();
    }

    @Bean
    public KafkaTemplate<String, Message> kafkaChatTemplate() {
        return new KafkaTemplate<>(messageProducerFactory());
    }

    // Notification 템플릿
    @Bean
    public ProducerFactory<String, Notification> notificationProducerFactory() {
        return new DefaultKafkaProducerFactory<>(notificationProducerConfigurations());
    }

    @Bean
    public Map<String, Object> notificationProducerConfigurations() {
        return ImmutableMap.<String, Object>builder()
                .put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer)
                .put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer)
                .put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer)
                .build();
    }

    @Bean
    public KafkaTemplate<String, Notification> kafkaNotificationTemplate() {
        return new KafkaTemplate<>(notificationProducerFactory());
    }
}
