package ru.k0r0tk0ff.sequence.processor.domain;

import java.util.ArrayList;
import java.util.Collection;

public class SequenceImpl implements Sequence {
    private Collection<Integer> collection;
    private int maxValue;
    private int firstValue;
    private int lastValue;

    public SequenceImpl(Collection<Integer> collection) {
        this.collection = collection;
        this.maxValue = collection.size();
    }

    private SequenceImpl(int maxValue) throws SeqenceException {

        if(maxValue == 0 || maxValue < 0 ) {
            throw new SeqenceException(" Entered max value is incorrect! ");
        }
        this.maxValue = maxValue;
        Collection<Integer> collection = new ArrayList<>();
        for (int i = 1; i <= maxValue; i++) {
            collection.add(i);
        }
    }

    private SequenceImpl(int firstValue, int lastValue) {
        this.firstValue = firstValue;
        this.lastValue = lastValue;
    }

    public Collection<Integer> get() {
        return collection;
    }

    public int firstValue() {
        return 1;
    }

    public int lastValue() {
        return maxValue;
    }
}
