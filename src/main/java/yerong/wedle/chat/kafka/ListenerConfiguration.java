package yerong.wedle.chat.kafka;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import yerong.wedle.chat.domain.chatting.Message;
import yerong.wedle.chat.domain.notification.Notification;

@EnableKafka
@Configuration
public class ListenerConfiguration {

    @Value("${kafka.bootstrap-servers}")
    private String kafkaServer;

    @Value("${kafka.consumer.group-id}")
    private String kafkaConsumerId;

    @Value("${kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    @Value("${kafka.consumer.fetch-min-bytes}")
    private String fetchMinBytes;

    @Value("${kafka.consumer.fetch-max-wait-ms}")
    private String fetchMaxWaitMs;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Message> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Message> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(kafkaConsumerFactory());
        factory.setConcurrency(2);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Message> kafkaConsumerFactory() {
        JsonDeserializer<Message> deserializer = new JsonDeserializer<>();
        deserializer.addTrustedPackages("yerong.wedle.chat.domain");

        Map<String, Object> consumerConfigurations = ImmutableMap.<String, Object>builder()
                .put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer)
                .put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerId)
                .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class)
                .put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer)
                .put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset)
                .put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, fetchMinBytes)
                .put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, fetchMaxWaitMs)
                .build();
        return new DefaultKafkaConsumerFactory<>(consumerConfigurations, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Notification> kafkaListenerContainerFactory2() {
        ConcurrentKafkaListenerContainerFactory<String, Notification> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(kafkaNotificationConsumer());
        factory.setConcurrency(2);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Notification> kafkaNotificationConsumer() {
        JsonDeserializer<Notification> deserializer = new JsonDeserializer<>();
        deserializer.addTrustedPackages("yerong.wedle.chat.domain");

        Map<String, Object> consumerConfigurations = ImmutableMap.<String, Object>builder()
                .put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer)
                .put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerId)
                .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class)
                .put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer)
                .put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset)
                .put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, fetchMinBytes)
                .put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, fetchMaxWaitMs)
                .build();
        return new DefaultKafkaConsumerFactory<>(consumerConfigurations, new StringDeserializer(), deserializer);
    }
}
