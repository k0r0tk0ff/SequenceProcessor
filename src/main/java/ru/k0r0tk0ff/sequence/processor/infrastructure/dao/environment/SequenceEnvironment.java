package ru.k0r0tk0ff.sequence.processor.infrastructure.dao.environment;

/**
 * Create table environment in DB for Dao
 */
public interface SequenceEnvironment {

    void clearSequenceStorage() throws SequenceEnvironmentException;

    void createSequenceStorage() throws SequenceEnvironmentException;

}
