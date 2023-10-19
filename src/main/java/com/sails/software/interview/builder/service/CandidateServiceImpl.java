package com.sails.software.interview.builder.service;

import com.sails.software.interview.builder.entity.CandidateEntity;
import com.sails.software.interview.builder.model.Candidate;
import com.sails.software.interview.builder.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl {
    private final CandidateRepository candidateRepository;

    public List<CandidateEntity> getCandidates() {
        return (List<CandidateEntity>) candidateRepository.findAll();

    }
}
