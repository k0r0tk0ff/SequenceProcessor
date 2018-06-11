package ru.k0r0tk0ff.sequence.processor.xslt;

/**
 * Work with XSLT data transformation
 */
public interface XsltTransformer {
    void transform(String xmlFileName, String xsltFileName, String resultXmlFileName)
            throws Exception;
}
