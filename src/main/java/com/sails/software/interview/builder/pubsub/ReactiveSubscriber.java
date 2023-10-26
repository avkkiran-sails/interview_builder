package com.sails.software.interview.builder.pubsub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gcp.pubsub.support.AcknowledgeablePubsubMessage;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

@Component
@Slf4j
public class ReactiveSubscriber {
    private final PubSubAsyncFlux pubSubAsyncFlux;
    private Disposable subscription;
    private boolean disposing;
    private String subscriptionName;

    public ReactiveSubscriber(PubSubAsyncFlux pubSubAsyncFlux) {
        this.pubSubAsyncFlux = pubSubAsyncFlux;
    }

    public void subscribe(ReactiveSubscription reactiveSubscription, String subscriptionName) {
        log.info("Subscribing {} to subscription {}", reactiveSubscription.getClass().getSimpleName(), subscriptionName);
        this.subscriptionName = subscriptionName;
        final Flux<AcknowledgeablePubsubMessage> flux = pubSubAsyncFlux.poll(subscriptionName);
        subscription = reactiveSubscription
                .processing(flux.limitRate(1))
                .doOnError(throwable -> logUnhandledException(reactiveSubscription.getClass(), throwable))
                .doOnCancel(() -> log("{} subscriber cancelled", reactiveSubscription.getClass()))
                .doOnTerminate(() -> log("{} subscriber terminated", reactiveSubscription.getClass()))
                .doOnComplete(() -> log("{} subscriber completed", reactiveSubscription.getClass()))
                .subscribe();
    }

    private void logUnhandledException(Class<?> type, Throwable throwable) {
        if (disposing) {
            log.warn("Unhandled error in subscriber {}", type, throwable);
        } else {
            log.error("Unhandled error in subscriber {}", type, throwable);
        }
    }

    private void log(String message, Class<?> type) {
        if (disposing) {
            log.warn(message, type);
        } else {
            log.error(message, type);
        }
    }

    public void destroy() {
        disposing = true;
        log.info("destroying reactive subscriber for {}", subscriptionName);
        subscription.dispose();
    }
}
