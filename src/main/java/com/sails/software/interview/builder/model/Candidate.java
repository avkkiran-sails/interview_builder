package com.sails.software.interview.builder.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Candidate {
    private Long id;
    private String name;
    private int experience;
}
