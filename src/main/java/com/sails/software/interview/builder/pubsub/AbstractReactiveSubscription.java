package com.sails.software.interview.builder.pubsub;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

@RequiredArgsConstructor
@AllArgsConstructor
public abstract class AbstractReactiveSubscription implements
        ApplicationListener<ApplicationReadyEvent>,
        DisposableBean,
        ReactiveSubscription {

    @Autowired
    private ReactiveSubscriber reactiveSubscriber;

    @Value("${pubsub.subscription}")
    private final String subscriptionName;

    @Override
    public void onApplicationEvent(@SuppressWarnings("NullableProblems") ApplicationReadyEvent event) {
        System.out.println("Inside onApplicationEvent");
        reactiveSubscriber.subscribe(this, subscriptionName);
    }

    @Override
    public void destroy() {
        reactiveSubscriber.destroy();
    }
}

