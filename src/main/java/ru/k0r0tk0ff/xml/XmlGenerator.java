package ru.k0r0tk0ff.xml;

import java.util.Collection;

/**
 * Describe how to generate XML
 */
public interface XmlGenerator {

    void generateXml(Collection<String> list) throws Exception;
}
