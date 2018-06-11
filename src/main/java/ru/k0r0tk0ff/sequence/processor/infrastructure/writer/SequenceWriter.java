package ru.k0r0tk0ff.sequence.processor.infrastructure.writer;

import ru.k0r0tk0ff.sequence.processor.domain.Sequence;

public interface SequenceWriter {
    void write(Sequence sequence) throws Exception;
}
