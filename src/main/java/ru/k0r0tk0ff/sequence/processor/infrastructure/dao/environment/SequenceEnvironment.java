package ru.k0r0tk0ff.sequence.processor.infrastructure.dao.environment;

import ru.k0r0tk0ff.sequence.processor.infrastructure.dao.SequenceDaoException;

public interface SequenceEnvironment {

    void clearSequenceStorage() throws SequenceDaoException;

    void createSequenceStorage() throws SequenceDaoException;
}
