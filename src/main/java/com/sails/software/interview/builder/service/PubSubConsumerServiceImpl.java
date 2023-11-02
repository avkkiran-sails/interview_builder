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
                fluxSink ->
                        fluxSink.onRequest(
                                count -> {
                                    try {
                                        System.out.println("count: " + count);
                                        pull(subscriptionName, count, fluxSink);
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                }
                        )
        );
    }

    private void pull(String subscriptionName, long numRequested,
                      FluxSink<AcknowledgeablePubsubMessage> fluxSink) {
        int intDemand = numRequested > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) numRequested;
        System.out.println("intDemand: " + intDemand);

        try {
            this.pubSubTemplate.pullAsync(subscriptionName, intDemand, false).whenComplete(
                    (messages, exception) -> {
                        System.out.println("Messages size: " + messages.size());
                        if (!fluxSink.isCancelled()) {
                            messages.forEach(fluxSink::next);
                            long numToPull = numRequested - messages.size();
                            System.out.println("numToPull: " + numToPull);
                            if (numToPull > 0) {
                                System.out.println("Inside numToPull");
                                try {
                                    pull(subscriptionName, numToPull, fluxSink);
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                            }
                        }
                    });
            //TODO:: Dispose after use
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private Flux<?> processing(Flux<AcknowledgeablePubsubMessage> flux) {
        return flux.flatMap(this::processMessage);
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
