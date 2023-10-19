package com.sails.software.interview.builder.controller;

import com.sails.software.interview.builder.model.HelloWorld;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/hello")
    public HelloWorld helloWorld() {
        return new HelloWorld( "Hello Sails world!");
    }
}
