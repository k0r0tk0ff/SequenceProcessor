package ru.k0r0tk0ff.sequence.processor.infrastructure.writer;

import java.util.Collection;

public interface SequenceWriter {

    void write(Collection<String> list) throws Exception;
}
