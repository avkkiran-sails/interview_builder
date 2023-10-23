package com.sails.software.interview.builder.model.ai.chatgpt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Message {
    public String role;
    public String content;
}
