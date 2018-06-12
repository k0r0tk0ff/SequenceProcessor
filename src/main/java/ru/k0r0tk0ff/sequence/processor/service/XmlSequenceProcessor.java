package ru.k0r0tk0ff.sequence.processor.service;

import ru.k0r0tk0ff.sequence.processor.domain.Sequence;
import ru.k0r0tk0ff.sequence.processor.infrastructure.dao.SequenceDao;
import ru.k0r0tk0ff.sequence.processor.infrastructure.writer.SequenceWriter;
import ru.k0r0tk0ff.sequence.processor.utils.xml.parser.SequenceParser;
import ru.k0r0tk0ff.sequence.processor.utils.xml.parser.XmlSequenceParser;
import ru.k0r0tk0ff.sequence.processor.utils.xslt.transformer.XsltTransformer;
import ru.k0r0tk0ff.sequence.processor.utils.xslt.transformer.XsltTransformerImpl;

import java.util.Collection;

public class XmlSequenceProcessor implements SequenceProcessor {
    private SequenceDao sequenceDao;
    private SequenceWriter sequenceWriter;
    private String pathToXmlSource;
    private String pathToXsltTemplateFile;
    private String pathToResultXmlFile;

    public XmlSequenceProcessor(SequenceDao sequenceDao,
                                SequenceWriter sequenceWriter,
                                String pathToXmlSource,
                                String pathToXsltTemplateFile,
                                String pathToResultXmlFile) {
        this.sequenceDao = sequenceDao;
        this.sequenceWriter = sequenceWriter;
        this.pathToXmlSource = pathToXmlSource;
        this.pathToXsltTemplateFile = pathToXsltTemplateFile;
        this.pathToResultXmlFile = pathToResultXmlFile;
    }

    public void process(int maxValue) throws Exception {
        long sumResult;
        Collection<Integer> collectionAfterParse;
        doSaveToStorage(maxValue);
        doCreateXml();
        doTransform();
        collectionAfterParse = doParse();
        sumResult = sum(collectionAfterParse);
        System.out.println("\nSum of sequence elements 1..." + maxValue + " is " + sumResult);
    }

    public void doSaveToStorage(int maxValue) throws Exception {
        sequenceDao.put(maxValue);
    }

    public void doCreateXml() throws Exception {
        Sequence sequence = sequenceDao.get();
        sequenceWriter.write(sequence);
    }

    public void doTransform() throws Exception {
        XsltTransformer xsltTransformer = new XsltTransformerImpl();
        xsltTransformer.transform(pathToXmlSource, pathToXsltTemplateFile, pathToResultXmlFile);
    }

    public Collection<Integer> doParse() throws Exception {
        SequenceParser parser = new XmlSequenceParser();
        return parser.doParse(pathToResultXmlFile);
    }

    public long sum(Collection<Integer> collection) {
        return collection.stream().reduce((s1,s2) -> s1+s2).orElse(0);
    }
}
