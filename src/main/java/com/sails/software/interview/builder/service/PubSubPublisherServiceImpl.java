package com.sails.software.interview.builder.service;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PubSubPublisherServiceImpl {

    @Value("${pubsub.topic}")
    private String topic;

    public void publishMessage(String message){
        try {
            TopicName topicName = TopicName.of("interviewbuilder", topic);
            Publisher publisher = Publisher.newBuilder(topicName).build();
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(ByteString.copyFromUtf8(message)).build();
            var messageId = publisher.publish(pubsubMessage).get();
            System.out.println(messageId);
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
