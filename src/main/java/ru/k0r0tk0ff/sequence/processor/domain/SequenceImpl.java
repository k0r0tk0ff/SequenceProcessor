package ru.k0r0tk0ff.sequence.processor.domain;

import java.util.ArrayList;
import java.util.Collection;

public class SequenceImpl implements Sequence {
    private Collection<Integer> collection;
    private int maxValue;

    private SequenceImpl(int maxValue) throws SeqenceException {
        this.maxValue = maxValue;
        Collection<Integer> collection = new ArrayList<>();
        for (int i = 1; i <= maxValue; i++) {
            collection.add(i);
        }
        this.collection = collection;
    }

    public Collection<Integer> get() {
        return collection;
    }

    public int getMaxValue() {
        return maxValue;
    }
}
