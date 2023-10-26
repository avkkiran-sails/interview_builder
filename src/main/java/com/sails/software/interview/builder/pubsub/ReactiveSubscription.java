package com.sails.software.interview.builder.pubsub;

import org.springframework.cloud.gcp.pubsub.support.AcknowledgeablePubsubMessage;
import reactor.core.publisher.Flux;

public interface ReactiveSubscription {
    Flux<?> processing(Flux<AcknowledgeablePubsubMessage> flux);
}
