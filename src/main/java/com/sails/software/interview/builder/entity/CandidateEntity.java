package com.sails.software.interview.builder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "candidate")
@Entity
@Setter
@Getter
public class CandidateEntity {
    @Id
    @SequenceGenerator(name = "candidate_seq",
            sequenceName = "candidate_seq",
            initialValue = 1000,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidate_seq")
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private int experience;
}
