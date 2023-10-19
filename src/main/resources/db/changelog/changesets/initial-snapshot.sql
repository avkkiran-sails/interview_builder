-- liquibase formatted sql

-- changeset avk:interview-builder-1
CREATE SEQUENCE IF NOT EXISTS sails."candidate_seq" AS BIGINT start with 1000 increment by 1;

-- changeset avk:interview-builder-2
CREATE TABLE sails."candidate" ("id" BIGINT DEFAULT NEXTVAL('sails."candidate_seq"') NOT NULL, name varchar(255) NOT NULL, experience int8 NOT NULL);