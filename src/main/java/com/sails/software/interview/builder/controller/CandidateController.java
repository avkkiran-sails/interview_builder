package com.sails.software.interview.builder.controller;

import com.sails.software.interview.builder.model.Candidate;
import com.sails.software.interview.builder.model.CandidateQuestionsResponse;
import com.sails.software.interview.builder.service.CandidateServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateServiceImpl candidateService;
    @GetMapping("candidates")
    public List<Candidate> getCandidates() {
        return candidateService.getCandidates();
    }

    @GetMapping("candidates/{id}")
    public Candidate getCandidateById(@PathVariable Long id) {
        return candidateService.getCandidateById(id);
    }

    @GetMapping("candidates/{id}/questions")
    public CandidateQuestionsResponse getCandidateQuestions(@PathVariable Long id) {
        return candidateService.getCandidateQuestions(id);
    }
}
