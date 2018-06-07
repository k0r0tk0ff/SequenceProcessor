package ru.k0r0tk0ff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.k0r0tk0ff.configuration.Settings;
import ru.k0r0tk0ff.configuration.SettingsFromFile;
import ru.k0r0tk0ff.starter.Starter;
import ru.k0r0tk0ff.starter.StarterImpl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by k0r0tk0ff
 * @since +1.8
 */
public class Main {

    private static final Logger LOG  = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        Integer sum;
        Connection connection = null;
        InputStream input;

        //Get variables from file with settings - "parameters.properties"
        Settings settings = new SettingsFromFile();
        input = settings.getClass().getClassLoader().getResourceAsStream("parameters.properties");

        try {
            settings.load(input);

            Starter starter = new StarterImpl();
            starter.setN(Integer.parseInt(settings.getValue("n")));
            starter.setUrl(settings.getValue("jdbc.url"));
            starter.setLogin(settings.getValue("jdbc.login"));
            starter.setPassword(settings.getValue("jdbc.password"));

            connection = starter.createConnectionToDb();
            starter.createTableIfNotExist(connection);
            starter.createTableIfNotExist(null);
            starter.uploadDataToTable(connection);

            //  Get converted data from DB
            Collection<String> list = starter.getDataFromDb(connection);

            // Generate XML from result of query to DB
            starter.generateXml(list);

            //  Transform file "1.xml" to "2.xml" with XSLT
            starter.xsltTransform("1.xml", "Transform.xslt");

            //  Parse file to arraylist and get sum
            sum = starter.sumOfElements(starter.getDataFromResource("2.xml"));

            System.out.println("Sum = " + sum);
        } catch (Exception e) {
            LOG.error(".......................................................................");
            LOG.error(e.toString());
            if(LOG.isDebugEnabled()) {
                for (StackTraceElement s : e.getStackTrace()) {
                    LOG.debug(s.toString());
                }
                // ??
                // или просто оставить вместо блока выше if(LOG.isDebugEnabled()) следующее -
                //e.printStackTrace();
                // а так, ваще, если StackTraceElement в лог запихать,
                // то красивый лог получается )))
            }
            LOG.error(".......................................................................");
        } finally {
            if (connection != null)
                try {
                    connection.close();
                    if(LOG.isDebugEnabled()) {
                        LOG.debug(" Close connection success");
                    }
                } catch (SQLException e) {
                    LOG.error(e.toString());
                    LOG.error(".......................................................................");
                }

            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    LOG.error(e.toString());
                    LOG.error(".......................................................................");
                }
            }
        }
    }
}
