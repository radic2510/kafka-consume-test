package com.example.consumetest.service;

import com.example.consumetest.model.Coin;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerServiceImpl {

    private static final String TOPIC = "bitmoi-quotation";


    @KafkaListener(topics = TOPIC, groupId = "bitmoi", containerFactory = "coinKafkaListenerContainerFactory")
    public void consume(Coin coin) {


        System.out.printf("[Event] '%s %s %s \n", coin.getCoinId(), coin.getName(), coin.getPrice());
    }
}
