package ru.k0r0tk0ff.parser;

import java.util.Collection;

/**
 * Parse resource to data
 */
public interface SequenceParser {
    Collection<Integer> doParse(String resource) throws Exception;
}
