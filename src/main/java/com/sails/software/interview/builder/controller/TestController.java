package com.sails.software.interview.builder.controller;

import com.sails.software.interview.builder.model.HelloWorld;
import com.sails.software.interview.builder.service.PubSubConsumerServiceImpl;
import com.sails.software.interview.builder.service.PubSubPublisherServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class TestController {

    private final PubSubPublisherServiceImpl pubSubPublisherService;
    private final PubSubConsumerServiceImpl pubSubConsumerService;

    @GetMapping("/hello")
    public HelloWorld helloWorld() {
        return new HelloWorld( "Hello Sails world!");
    }

    @PostMapping(path = "/publishMessage/{message}")
    public void publishMessage(@PathVariable String message) {
        pubSubPublisherService.publishMessage(message);
    }

    @GetMapping("/consumeMessage")
    public void consumeMessage() {
        pubSubConsumerService.consumeMessage();
    }
}
