package ru.k0r0tk0ff;

import ru.k0r0tk0ff.configuration.Settings;
import ru.k0r0tk0ff.configuration.SettingsFromFile;
import ru.k0r0tk0ff.starter.Starter;
import ru.k0r0tk0ff.starter.StarterImpl;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by k0r0tk0ff
 * @since +1.8
 */
public class Main {
    public static void main(String[] args) {

        //Get variables from file with settings - "parameters.properties"
        Settings settings = new SettingsFromFile();
        settings.load();

        Starter starter = new StarterImpl();
        starter.setN(Integer.parseInt(settings.getValue("n")));
        starter.setUrl(settings.getValue("jdbc.url"));
        starter.setLogin(settings.getValue("jdbc.login"));
        starter.setPassword(settings.getValue("jdbc.password"));

        starter.createConnectionToDb();
        starter.createTableIfNotExist();
        starter.uploadDataToTable();

        //  Get converted data from DB
        Collection<String> list = starter.getDataFromDb();

        // Generate XML from result of query to DB
        starter.generateXml(list);


        //  Transform file "1.xml" to "2.xml" with XSLT
        starter.xsltTransform("1.xml", "Transform.xslt");

        /*
        //  Parse file to arraylist and get sum
        starter.xmlParserToArrayListAndSum("2.xml");*/

        starter.closeConnection();
    }
}
