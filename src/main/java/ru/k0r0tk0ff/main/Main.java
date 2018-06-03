package ru.k0r0tk0ff.main;



import ru.k0r0tk0ff.service.Settings;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;



/**
 * Created by k0r0tk0ff on 5/15/2017.
 * @since +1.6
 */
public class Main {



    public static void main(String[] args) throws SQLException, TransformerConfigurationException, XMLStreamException {

        //Get variables from file with settings - "parameters.properties"
        Settings settings = Settings.getInstance();

        Starter starter = new Starter();
        starter.setN(Integer.parseInt(settings.getValue("n")));
        starter.setUrl(settings.getValue("jdbc.url"));
        starter.setLogin(settings.getValue("jdbc.login"));
        starter.setPassword(settings.getValue("jdbc.password"));

        // Get connection to DB
        Connection connection = starter.getConnectionToDB();

        // Insert in to DB values
        starter.insertDataToDB(connection);

        //  Get data from DB
        ResultSet resultSet = starter.getDataFromDb(connection);

        // Generate XML from result of query to DB
        //xmlAsAString = starter.generateXml(resultSet);
        starter.generateXml(resultSet);

        //  Transform file "1.xml" to "2.xml" with XSLT
        starter.xsltTransform("1.xml");

        //  Parse file to arraylist and get sum
        starter.xmlParserToArrayListAndSum("2.xml");
    }
}
