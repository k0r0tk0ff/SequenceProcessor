package ru.k0r0tk0ff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.k0r0tk0ff.configuration.Settings;
import ru.k0r0tk0ff.configuration.SettingsFromFile;
import ru.k0r0tk0ff.starter.Starter;
import ru.k0r0tk0ff.starter.StarterImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by k0r0tk0ff
 *
 * @since +1.8
 */
public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        Integer sum;
        Connection connection = null;
        BufferedReader input = null;

        String propertiesFileName = "parameters.properties";
        String xsltFileName = "Transform.xslt";

        //Get variables from file with settings - "parameters.properties"

        Path path = Paths.get(propertiesFileName);

        try {

            input = Files.newBufferedReader(path, Charset.forName("UTF-8"));
            Settings settings = new SettingsFromFile(propertiesFileName, input);
            settings.load();

            Starter starter = new StarterImpl();
            starter.setN(Integer.parseInt(settings.getValue("n")));
            starter.setUrl(settings.getValue("jdbc.url"));
            starter.setLogin(settings.getValue("jdbc.login"));
            starter.setPassword(settings.getValue("jdbc.password"));

            connection = starter.createConnectionToDb();
            starter.uploadDataToTable(connection);

            //  Get converted data from DB
            Collection<String> list = starter.getDataFromDb(connection);

            // Generate XML from result of query to DB
            starter.generateXml(list);

            //  Transform file "1.xml" to "2.xml" with XSLT
            starter.xsltTransform("1.xml", xsltFileName);

            //  Parse file to arraylist and get sum
            sum = starter.sumOfElements(starter.getDataFromResource("2.xml"));

            System.out.println("Sum = " + sum);
        } catch (Exception e1) {
            LOG.error(".......................................................................");
            LOG.error("Application error!", e1);
        } finally {
            if (connection != null)
                try {
                    connection.close();
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(" Close connection success");
                    }
                } catch (SQLException e2) {
                    LOG.error(".......................................................................");
                    LOG.error("Application error!", e2);
                    if (input != null) {
                        try {
                            input.close();
                        } catch (IOException e3) {
                            LOG.error(".......................................................................");
                            LOG.error("Application error!", e3);
                        }
                    }
                }
        }
    }
}
