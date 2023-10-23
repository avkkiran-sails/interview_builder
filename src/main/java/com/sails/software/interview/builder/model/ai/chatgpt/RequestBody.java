package com.sails.software.interview.builder.model.ai.chatgpt;

import com.sails.software.interview.builder.model.ai.chatgpt.Message;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class RequestBody {
    public String model;
    public List<Message> messages;
    public int temperature = 1;
    public int max_tokens = 256;
    public int top_p = 1;
    public int frequency_penalty;
    public int presence_penalty;

    public RequestBody(String prompt) {
        List<Message> messages = new ArrayList<>();
        Message userMessage = new Message("user", "");
        Message systemMessage = new Message("system", prompt);
        messages.add(userMessage);
        messages.add(systemMessage);
        this.messages = messages;
    }
}
