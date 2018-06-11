package ru.k0r0tk0ff.sequence.processor.service;

import ru.k0r0tk0ff.sequence.processor.domain.Sequence;
import ru.k0r0tk0ff.sequence.processor.infrastructure.dao.SequenceDao;
import ru.k0r0tk0ff.sequence.processor.infrastructure.writer.SequenceWriter;
import ru.k0r0tk0ff.sequence.processor.utils.xslt.transformer.XsltTransformer;
import ru.k0r0tk0ff.sequence.processor.utils.xslt.transformer.XsltTransformerImpl;

public class XmlSequenceProcessor extends AbstractSequenceProcessor {
    private SequenceDao sequenceDao;
    private SequenceWriter sequenceWriter;

    public XmlSequenceProcessor(SequenceDao sequenceDao, SequenceWriter sequenceWriter) {
        this.sequenceDao = sequenceDao;
        this.sequenceWriter = sequenceWriter;
    }

    @Override
    public void saveToStorage(int maxValue) throws Exception {
        sequenceDao.put(maxValue);
    }

    @Override
    public void createXml() throws Exception {
        Sequence sequence = sequenceDao.get();
        sequenceWriter.write(sequence);
    }

    @Override
    public void transform() throws Exception {
        XsltTransformer xsltTransformer = new XsltTransformerImpl();
        xsltTransformer.transform("", "");
    }

    @Override
    public long sum() {
        return 0;
    }
}
