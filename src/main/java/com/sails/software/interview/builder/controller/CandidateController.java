package com.sails.software.interview.builder.controller;

import com.sails.software.interview.builder.entity.CandidateEntity;
import com.sails.software.interview.builder.model.Candidate;
import com.sails.software.interview.builder.service.CandidateServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateServiceImpl candidateService;
    @GetMapping("candidates")
    public List<CandidateEntity> getCandidates() {
        return candidateService.getCandidates();
    }
}
