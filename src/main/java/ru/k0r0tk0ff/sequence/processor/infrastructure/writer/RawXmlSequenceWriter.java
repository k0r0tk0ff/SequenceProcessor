package ru.k0r0tk0ff.sequence.processor.infrastructure.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.k0r0tk0ff.sequence.processor.domain.Sequence;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.Collection;

public class RawXmlSequenceWriter implements SequenceWriter {
    private static final Logger LOG = LoggerFactory.getLogger(RawXmlSequenceWriter.class);
    private String pathToXmlSource;
    private String pathToXmlTarget;

    public RawXmlSequenceWriter(String pathToXmlSource, String pathToXmlTarget) {
        this.pathToXmlSource = pathToXmlSource;
        this.pathToXmlTarget = pathToXmlTarget;
    }

    public void write(Sequence sequence) throws WriterException {
        String line;
        final XMLOutputFactory factory = XMLOutputFactory.newFactory();
        try {
            XMLStreamWriter writer = factory.createXMLStreamWriter(
                    new FileOutputStream(pathToXmlTarget), "UTF-8");
            writer.writeStartDocument("UTF-8", "1.0");
            writer.writeStartElement("entries");
            Collection<Integer> collection = sequence.get();
            for (Integer aDbQueryResult : collection) {
                writer.writeStartElement("entry");
                writer.writeStartElement("field");
                writer.writeCharacters(String.valueOf(aDbQueryResult));
                writer.writeEndElement();
                writer.writeEndElement();
            }
            writer.writeEndElement();
            writer.writeEndDocument();
            writer.close();

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(new StreamSource(
                            new BufferedInputStream(new FileInputStream(pathToXmlTarget))),
                    new StreamResult(new FileOutputStream(pathToXmlSource))
            );

            StringBuilder stringBuilder = new StringBuilder();
            String ls = System.getProperty("line.separator");

            BufferedReader reader = new BufferedReader(new FileReader(pathToXmlSource));
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
        } catch (XMLStreamException | TransformerException | IOException e) {
            throw new WriterException();
        }
        LOG.debug(" Write xml success");
    }
}

