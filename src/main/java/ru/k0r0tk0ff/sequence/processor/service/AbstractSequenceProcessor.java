package ru.k0r0tk0ff.sequence.processor.service;

public abstract class AbstractSequenceProcessor implements SequenceProcessor {
    @Override
    public void process(int maxValue) throws Exception {
        saveToStorage(maxValue);
        createXml();
        transform();
        sum();
    }

    public abstract void saveToStorage(int maxValue) throws Exception;

    public abstract void createXml() throws Exception;

    public abstract void transform() throws Exception;

    public abstract long sum();
}
