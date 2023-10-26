package com.sails.software.interview.builder.pubsub;

import com.google.api.gax.rpc.DeadlineExceededException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.support.AcknowledgeablePubsubMessage;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Component
@Slf4j
@RequiredArgsConstructor
public class PubSubAsyncFlux {
    private final PubSubTemplate pubSubTemplate;
    public Flux<AcknowledgeablePubsubMessage> poll(String subscriptionName) {
        return Flux.create(
                sink ->
                        sink.onRequest(
                                count -> {
                                    pull(subscriptionName, count, sink);
                                }
                        )
        );
    }
    private void pull(String subscriptionName, long numRequested,
                      FluxSink<AcknowledgeablePubsubMessage> sink) {
        int intDemand = numRequested > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) numRequested;
        this.pubSubTemplate.pullAsync(subscriptionName, intDemand, false).addCallback(
                messages -> {
                    if (!sink.isCancelled()) {
                        messages.forEach(sink::next);
                    }
                    if (!sink.isCancelled()) {
                        long numToPull = numRequested - messages.size();
                        if (numToPull > 0) {
                            pull(subscriptionName, numToPull, sink);
                        }
                    }
                },
                exception -> {
                    if (exception instanceof DeadlineExceededException) {
                        log.info("Blocking pull timed out due to empty subscription "
                                + subscriptionName
                                + "; retrying.");
                        pull(subscriptionName, numRequested, sink);
                    } else {
                        sink.error(exception);
                    }
                });
    }
}

