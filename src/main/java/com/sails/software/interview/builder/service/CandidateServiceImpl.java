package com.sails.software.interview.builder.service;

import com.sails.software.interview.builder.entity.CandidateEntity;
import com.sails.software.interview.builder.mapper.CandidateMapper;
import com.sails.software.interview.builder.model.Candidate;
import com.sails.software.interview.builder.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl {
    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    public List<Candidate> getCandidates() {
        List<CandidateEntity> candidateEntities = (List<CandidateEntity>)
                candidateRepository.findAll();
        return candidateMapper.toItemList(candidateEntities);
    }
}
