package ru.k0r0tk0ff.xslt;

/**
 * Work with XSLT data transformation
 */
public interface XsltTransformer {
    void transform(String xmlFileName, String xsltFileName) throws Exception;
}
