package ru.k0r0tk0ff.parser;

import java.util.Collection;

/**
 * Parse resource to data
 */
public interface SequenceParser {
    Collection<Integer> parser(String resource) throws Exception;
}
