package com.sails.software.interview.builder.service;

import com.sails.software.interview.builder.entity.CandidateEntity;
import com.sails.software.interview.builder.mapper.CandidateMapper;
import com.sails.software.interview.builder.model.Candidate;
import com.sails.software.interview.builder.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

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

    public Candidate getCandidateById(Long id) {
        Candidate candidate = null;
        Optional<CandidateEntity> candidateEntity = candidateRepository.findById(id);
        if(candidateEntity.isPresent()) {
            candidate = candidateMapper.toItem(candidateEntity.get());
        }
        return candidate;
    }
}
