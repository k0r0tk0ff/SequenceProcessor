package ru.k0r0tk0ff.sequence;

/**
 * Create table environment in DB for Dao
 */
public interface SequenceEnvironment {

    void clearSequence() throws Exception;

    void createSequenceInStorage() throws Exception;

}
