package ru.k0r0tk0ff.sequence.processor.infrastructure.dao.environment;

public interface SequenceEnvironment {

    void clearSequenceStorage() throws SequenceEnvironmentException;

    void createSequenceStorage() throws SequenceEnvironmentException;
}
