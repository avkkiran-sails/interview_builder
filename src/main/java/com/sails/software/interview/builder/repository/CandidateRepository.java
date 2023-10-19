package com.sails.software.interview.builder.repository;

import com.sails.software.interview.builder.entity.CandidateEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends CrudRepository<CandidateEntity, Long> {
}
