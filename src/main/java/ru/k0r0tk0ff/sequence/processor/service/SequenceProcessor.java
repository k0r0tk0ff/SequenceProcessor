package ru.k0r0tk0ff.sequence.processor.service;

import java.util.Collection;

public interface SequenceProcessor {
    void process(int maxValue) throws Exception;
    void doSaveToStorage(int maxValue) throws Exception;
    void doCreateXml() throws Exception;
    void doTransform() throws Exception;
    Collection<Integer> doParse() throws Exception;
    long sum(Collection<Integer> collection);
}
