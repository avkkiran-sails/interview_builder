-- liquibase formatted sql


--changeset author:candidate-555
INSERT INTO sails.candidate(name, experience)
VALUES('TestCandidate555',5);

--changeset author:candidate-444
INSERT INTO sails.candidate(name, experience)
VALUES('TestCandidate444',4);

--changeset author:candidate-333
INSERT INTO sails.candidate(name, experience)
VALUES('TestCandidate333',3);

--changeset author:candidate-222
INSERT INTO sails.candidate(name, experience)
VALUES('TestCandidate222',2);

--changeset author:candidate-111
INSERT INTO sails.candidate(name, experience)
VALUES('TestCandidate111',3);

--changeset author:candidate-000
INSERT INTO sails.candidate(name, experience)
VALUES('TestCandidate000',2);
