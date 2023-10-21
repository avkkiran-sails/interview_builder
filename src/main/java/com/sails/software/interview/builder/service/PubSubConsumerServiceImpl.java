package com.sails.software.interview.builder.service;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PubSubConsumerServiceImpl {

    private final PubSubTemplate publisher;

    @Value("${pubsub.subscription}")
    private String subscription;

    public void consumeMessage(){
        try {
            var messages = publisher.pullAsync(subscription, 100, true);
            for (var message: messages.get()){
                System.out.println(message.getPubsubMessage().getData());
                message.ack();
            }

        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
