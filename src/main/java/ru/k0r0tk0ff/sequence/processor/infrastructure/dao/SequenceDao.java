package ru.k0r0tk0ff.sequence.processor.infrastructure.dao;

import ru.k0r0tk0ff.sequence.processor.domain.Sequence;

public interface SequenceDao {

    void put(int n) throws PostgresSequenceDaoException;

    Sequence get() throws PostgresSequenceDaoException;

}
