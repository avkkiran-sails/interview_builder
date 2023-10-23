package com.sails.software.interview.builder.model;

import com.sails.software.interview.builder.model.ai.chatgpt.Choice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CandidateQuestionsResponse {
    private List<Choice> choices;
}
