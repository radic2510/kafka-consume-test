package com.example.consumetest.config;

import com.example.consumetest.model.Coin;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootStrapServers;

    @Bean
    public ConsumerFactory<String, Coin> coinConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "bitmoi");

//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
//        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, JsonDeserializer.class);
//        props.put(JsonDeserializer.KEY_DEFAULT_TYPE, "com.codecheckin.quotation");
//        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
//        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.example.MyValue");
//        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.example");


        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new ErrorHandlingDeserializer(new JsonDeserializer<>(Coin.class, false))
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Coin> coinKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Coin> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(coinConsumerFactory());
        return factory;
    }
}
