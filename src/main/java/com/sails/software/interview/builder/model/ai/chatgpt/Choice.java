package com.sails.software.interview.builder.model.ai.chatgpt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Choice {
    private int index;
    private Message message;
}
