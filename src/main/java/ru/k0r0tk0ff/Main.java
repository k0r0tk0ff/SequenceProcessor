package ru.k0r0tk0ff;

import ru.k0r0tk0ff.configuration.Settings;
import ru.k0r0tk0ff.configuration.SettingsFromFile;
import ru.k0r0tk0ff.starter.Starter;

import java.sql.ResultSet;

/**
 * Created by k0r0tk0ff
 * @since +1.8
 */
public class Main {
    public static void main(String[] args) {

        //Get variables from file with settings - "parameters.properties"
        Settings settings = new SettingsFromFile();
        settings.loadConfigurationSettings();

        Starter starter = new Starter();
        starter.setN(Integer.parseInt(settings.getValue("n")));
        starter.setUrl(settings.getValue("jdbc.url"));
        starter.setLogin(settings.getValue("jdbc.login"));
        starter.setPassword(settings.getValue("jdbc.password"));

        starter.initializeDataResources();
        starter.createTableIfNotExistAndFillData();

        //  Get data from DB
        ResultSet resultSet = starter.getDataFromDb();


      /*  // Generate XML from result of query to DB
        //xmlAsAString = starter.generateXml(resultSet);
        starter.generateXml(resultSet);

        //  Transform file "1.xml" to "2.xml" with XSLT
        starter.xsltTransform("1.xml");

        //  Parse file to arraylist and get sum
        starter.xmlParserToArrayListAndSum("2.xml");*/

        starter.closeConnection();
    }
}
