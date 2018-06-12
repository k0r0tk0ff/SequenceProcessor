package ru.k0r0tk0ff.sequence.processor.domain;

import java.util.ArrayList;
import java.util.Collection;

public class SequenceImpl implements Sequence {
    private Collection<Integer> collection;

    public SequenceImpl(Collection<Integer> collection) {
        this.collection = collection;
    }

    public Collection<Integer> get() {
        return collection;
    }
}
