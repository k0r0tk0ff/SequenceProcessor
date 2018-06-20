package ru.k0r0tk0ff.sequence.processor.service;

import ru.k0r0tk0ff.sequence.processor.infrastructure.dao.SequenceDaoException;
import ru.k0r0tk0ff.sequence.processor.infrastructure.writer.WriterException;
import ru.k0r0tk0ff.sequence.processor.utils.UtilsException;

import java.util.Collection;

public interface SequenceProcessor {
    void process(int maxValue) throws
            SequenceDaoException,
            WriterException,
            UtilsException;
    void doSaveToStorage(int maxValue) throws SequenceDaoException;
    void doCreateXml() throws WriterException, SequenceDaoException;
    void doTransform() throws UtilsException;
    Collection<Integer> doParse() throws UtilsException;
    long sum(Collection<Integer> collection);
}
