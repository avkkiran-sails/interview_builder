package com.sails.software.interview.builder.service;

import com.sails.software.interview.builder.entity.CandidateEntity;
import com.sails.software.interview.builder.mapper.CandidateMapper;
import com.sails.software.interview.builder.model.Candidate;
import com.sails.software.interview.builder.model.CandidateQuestionsResponse;
import com.sails.software.interview.builder.model.ai.chatgpt.Choice;
import com.sails.software.interview.builder.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl {
    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private final AIServiceImpl aiService;

    @Value("${ai.chatgpt.prompt.candidate.questions}")
    private String candidateQuestionsPrompt;

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

    public CandidateQuestionsResponse getCandidateQuestions(Long id) {
        Candidate candidate = getCandidateById(id);
        String prompt = candidateQuestionsPrompt.replace(
                "{EXP}", String.valueOf(candidate.getExperience()));
        List<Choice> response = aiService.getResponse(prompt);
        return new CandidateQuestionsResponse(response);
    }
}
