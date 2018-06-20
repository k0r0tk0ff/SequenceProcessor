package ru.k0r0tk0ff.sequence.processor.utils.xslt.transformer;

import ru.k0r0tk0ff.sequence.processor.utils.UtilsException;

public interface XsltTransformer {
    void transform(String xmlFileName, String xsltFileName, String resultXmlFileName)
            throws UtilsException;
}
