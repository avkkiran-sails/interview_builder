package com.sails.software.interview.builder.service;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PubSubPublisherServiceImpl {

    private final PubSubTemplate publisher;

    @Value("${pubsub.topic}")
    private String topic;

    public void publishMessage(String message){
        try {
            var messageId = publisher.publish(topic, message).get();
            System.out.println(messageId);
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
