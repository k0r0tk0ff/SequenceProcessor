package ru.k0r0tk0ff.sequence.processor.infrastructure.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.Collection;

/**
 * Use for generate file with writer
 */
public class XmlSequenceWriter implements SequenceWriter {
    String xmlFileName;
    String xmlFileNameWithRawData;

    public XmlSequenceWriter(String xmlFileName, String xmlFileNameWithRawData) {
        this.xmlFileName = xmlFileName;
        this.xmlFileNameWithRawData = xmlFileNameWithRawData;
    }

    private static final Logger LOG = LoggerFactory.getLogger(XmlSequenceWriter.class);

    public void write(Collection<String> seqAsList) throws Exception{
        String line;
        final XMLOutputFactory factory = XMLOutputFactory.newFactory();
        XMLStreamWriter writer = factory.createXMLStreamWriter(
                new FileOutputStream(xmlFileNameWithRawData), "UTF-8");
        writer.writeStartDocument("UTF-8", "1.0");
        writer.writeStartElement("entries");
        for (String aDbQueryResult : seqAsList) {
            writer.writeStartElement("entry");
            writer.writeStartElement("field");
            writer.writeCharacters(aDbQueryResult);
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
        transformer.setOutputProperty("{http://writer.apache.org/xslt}indent-amount", "2");
        transformer.transform(new StreamSource(
                        new BufferedInputStream(new FileInputStream(xmlFileNameWithRawData))),
                new StreamResult(new FileOutputStream(xmlFileName))
        );

        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        BufferedReader reader = new BufferedReader(new FileReader(xmlFileName));
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }

        LOG.debug(" Write writer success");
    }
}

