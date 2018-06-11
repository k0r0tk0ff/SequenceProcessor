package ru.k0r0tk0ff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.k0r0tk0ff.sequence.processor.infrastructure.configuration.Configuration;
import ru.k0r0tk0ff.sequence.processor.infrastructure.configuration.PropertiesFileConfiguration;
import ru.k0r0tk0ff.sequence.processor.infrastructure.configuration.PropertiesFileLoadException;
import ru.k0r0tk0ff.sequence.processor.infrastructure.configuration.Settings;
import ru.k0r0tk0ff.sequence.processor.infrastructure.configuration.SettingsFromFile;
import ru.k0r0tk0ff.sequence.processor.infrastructure.dao.PostgresSequenceDao;
import ru.k0r0tk0ff.sequence.processor.service.SequenceProcessor;
import ru.k0r0tk0ff.sequence.processor.service.XmlSequenceProcessor;
import ru.k0r0tk0ff.sequence.processor.infrastructure.dao.environment.PostgresSequenceEnvironment;
import ru.k0r0tk0ff.sequence.processor.infrastructure.writer.RawXmlSequenceWriter;

import java.sql.Connection;

/**
 * Created by k0r0tk0ff
 */
public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private static final String PROPERTIES_FILE_NAME = "parameters.properties";
    private static final String XML_FILE_NAME_WITH_RAW_DATA = "0.writer";
    private static final String XML_FILE_NAME = "1.writer";
    private static final String XML_FILE_NAME_FOR_PARSE = "2.writer";
    private static final String XSLT_FILE_NAME = "Transform.xslt";

    public static void main(String[] args) {

        //TODO Сделать ввод с клавы числа N


        try {
            PropertiesFileConfiguration.load(PROPERTIES_FILE_NAME);
            Configuration configuration = PropertiesFileConfiguration.getInstance();
            try (Connection connection =
                         new DbDataSource(
                                 configuration.getValue(""),
                                 configuration.getValue(""),
                                 configuration.getValue("")
                         ).getConnection()
            ) {
                SequenceProcessor xmlSequenceProcessor =
                        new XmlSequenceProcessor(
                                new PostgresSequenceDao(
                                        connection,
                                        new PostgresSequenceEnvironment(connection)
                                ),
                                new RawXmlSequenceWriter("", "")
                        );
                xmlSequenceProcessor.process(40);
            }
        } catch (PropertiesFileLoadException e) {
            LOG.error(" Failed to read the property file! ", e);
        } catch (Exception e) {
            LOG.error(" Application error! ", e);
        }
        //TODO Сделать обработку разных исключений
        /*} catch (Exception e) {
        LOG.error(" Application error! ", e);
        }*/
    }
}
