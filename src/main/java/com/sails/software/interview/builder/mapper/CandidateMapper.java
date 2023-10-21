package com.sails.software.interview.builder.mapper;

import com.sails.software.interview.builder.entity.CandidateEntity;
import com.sails.software.interview.builder.model.Candidate;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CandidateMapper {
    List<Candidate> toItemList(List<CandidateEntity> candidateEntityList);
}
