package ru.k0r0tk0ff.sequence.processor.xml;

import java.util.Collection;

/**
 * Describe how to generate XML
 */
public interface SequenceWriter {

    void write(Collection<String> list) throws Exception;
}
