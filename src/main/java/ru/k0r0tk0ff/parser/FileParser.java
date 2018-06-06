package ru.k0r0tk0ff.parser;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Parse file to data
 */
public class FileParser implements ParserResourceToData {

    public Collection<Integer> parser(String filename) throws Exception{
        Collection<Integer> parsedData = new ArrayList<>();

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader parser;
        parser = factory.createXMLStreamReader(
                    new BufferedInputStream(new FileInputStream(filename)));

        while (parser.hasNext()) {
                int event = parser.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    if (parser.getLocalName().equals("entry")) {
                        String intValueInAttribute = parser.getAttributeValue(null, "field");
                        if (intValueInAttribute != null) {
                            //System.out.println(intValueInAttribute);
                            parsedData.add(Integer.parseInt(intValueInAttribute));
                        }
                    }

                }
            }
        return parsedData;
    }
}
