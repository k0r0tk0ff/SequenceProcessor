package ru.k0r0tk0ff.xslt;

import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import ru.k0r0tk0ff.xml.XmlSequenceWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileOutputStream;


/**
 * Transform xml file with use xslt-transformation
 */
public class XsltTransformerImpl implements XsltTransformer {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(XsltTransformerImpl.class);

    public void transform(String xmlFileName, String xsltFileName, String resultXmlFileName)
            throws Exception {
        File stylesheet = new File(xsltFileName);
        File datafile = new File(xmlFileName);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder2 = factory.newDocumentBuilder();
        Document document2 = builder2.parse(datafile);

        // Use a Transformer for output
        TransformerFactory tFactory = TransformerFactory.newInstance();
        StreamSource styleSource = new StreamSource(stylesheet);

        Transformer transformer = tFactory.newTransformer(styleSource);

        //transformer.setParameter("format-pretty-print", true);
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","2");
        transformer.setOutputProperty(OutputKeys.STANDALONE,"yes");

        DOMSource source = new DOMSource(document2);

        transformer.transform(source, new StreamResult(new FileOutputStream(resultXmlFileName)));
    }
}
