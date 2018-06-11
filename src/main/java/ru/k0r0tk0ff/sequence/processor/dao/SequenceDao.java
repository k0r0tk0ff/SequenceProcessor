package ru.k0r0tk0ff.sequence.processor.dao;

import java.util.Collection;

public interface SequenceDao {

    void put(int n) throws Exception;

    Collection<String> get() throws Exception;

}
