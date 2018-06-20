package ru.k0r0tk0ff.sequence.processor.utils.xml.parser;

import ru.k0r0tk0ff.sequence.processor.utils.UtilsException;

import java.util.Collection;

public interface SequenceParser {
    Collection<Integer> doParse(String resource) throws UtilsException;
}
