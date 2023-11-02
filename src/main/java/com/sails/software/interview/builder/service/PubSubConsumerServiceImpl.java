package com.sails.software.interview.builder.service;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.support.AcknowledgeablePubsubMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class PubSubConsumerServiceImpl {

    private final PubSubTemplate pubSubTemplate;

    private Disposable subscription;

    @Value("${pubsub.subscription}")
    private String subscriptionTopic;

    public void consumeMessage() {
        try {
            System.out.println("Inside consumeMessage");
            var flux = this.poll(subscriptionTopic);
            subscription = this.processing(flux.limitRate(1)).subscribe();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Flux<AcknowledgeablePubsubMessage> poll(String subscriptionName) {
        return Flux.create(
                sink ->
                        sink.onRequest(
                                count -> {
                                    try {
                                        pull(subscriptionName, count, sink);
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                }
                        )
        );
    }

    private void pull(String subscriptionName, long numRequested,
                      FluxSink<AcknowledgeablePubsubMessage> sink) {
        int intDemand = numRequested > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) numRequested;

        try {
            this.pubSubTemplate.pullAsync(subscriptionName, intDemand, false).whenComplete(
                    (messages, exception) -> {
                        if (!sink.isCancelled()) {
                            messages.forEach(sink::next);
                        }
                        if (!sink.isCancelled()) {
                            long numToPull = numRequested - messages.size();
                            if (numToPull > 0) {
                                try {
                                    pull(subscriptionName, numToPull, sink);
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                            }
                        }
                    });
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private final Flux<?> processing(Flux<AcknowledgeablePubsubMessage> flux) {
        return flux.flatMap(this::processing);
    }

    private Mono<?> processing(AcknowledgeablePubsubMessage message) {
        return Mono
                .just(message)
                .flatMap(this::processMessage);
    }

    private Mono<?> processMessage(AcknowledgeablePubsubMessage message) {
        System.out.println(message.getPubsubMessage().getData());
        message.ack();
        return Mono.just(message);
    }


    @EventListener(ApplicationStartedEvent.class)
    public void startConsumer() {
        this.consumeMessage();
    }
}
