package ru.k0r0tk0ff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.k0r0tk0ff.sequence.processor.infrastructure.configuration.Configuration;
import ru.k0r0tk0ff.sequence.processor.infrastructure.configuration.PropertiesFileConfiguration;
import ru.k0r0tk0ff.sequence.processor.infrastructure.configuration.PropertiesFileLoadException;
import ru.k0r0tk0ff.sequence.processor.infrastructure.dao.PostgresSequenceDao;
import ru.k0r0tk0ff.sequence.processor.infrastructure.db.DbDataSourceException;
import ru.k0r0tk0ff.sequence.processor.infrastructure.db.DbDataSource;
import ru.k0r0tk0ff.sequence.processor.service.SequenceProcessor;
import ru.k0r0tk0ff.sequence.processor.service.XmlSequenceProcessor;
import ru.k0r0tk0ff.sequence.processor.infrastructure.dao.environment.PostgresSequenceEnvironment;
import ru.k0r0tk0ff.sequence.processor.infrastructure.writer.RawXmlSequenceWriter;
import ru.k0r0tk0ff.sequence.processor.service.XmlSequenceProcessorException;
import ru.k0r0tk0ff.sequence.processor.utils.input.sequense.parameters.ConsoleInput;
import ru.k0r0tk0ff.sequence.processor.utils.input.sequense.parameters.ConsoleInputException;
import ru.k0r0tk0ff.sequence.processor.utils.input.sequense.parameters.InputInterface;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private static final String PATH_TO_PROPERTIES_FILE = "parameters.properties";
    private static final String PATH_TO_XML_FILE_WITH_RAW_DATA = "0.xml";
    private static final String PATH_TO_XML_FILE = "1.xml";
    private static final String PATH_TO_XML_FILE_FOR_PARSE = "2.xml";
    private static final String PATH_TO_XSLT_FILE = "Transform.xslt";

    public static void main(String[] args) {

        //TODO Сделать ввод с клавы числа N

        try {
            PropertiesFileConfiguration.load(PATH_TO_PROPERTIES_FILE);
            Configuration configuration = PropertiesFileConfiguration.getInstance();
                try (Connection connection =
                             new DbDataSource(
                                     configuration.getValue("jdbc.url"),
                                     configuration.getValue("jdbc.login"),
                                     configuration.getValue("jdbc.password")
                            ).getConnection()
                ) {
                    InputInterface inputInterface = new ConsoleInput();
                    Integer seqMaxValue = inputInterface.getMaxValue();
                    SequenceProcessor xmlSequenceProcessor =
                            new XmlSequenceProcessor(
                                    new PostgresSequenceDao(
                                            connection,
                                            new PostgresSequenceEnvironment(connection)
                                    ),
                                    new RawXmlSequenceWriter(
                                            PATH_TO_XML_FILE_WITH_RAW_DATA,
                                            PATH_TO_XML_FILE),
                                    PATH_TO_XML_FILE,
                                    PATH_TO_XSLT_FILE,
                                    PATH_TO_XML_FILE_FOR_PARSE
                            );
                    xmlSequenceProcessor.process(seqMaxValue);
                } catch (Exception e) {
                    LOG.error(" Application error! ", e);
                }
        } catch (PropertiesFileLoadException e1) {
            LOG.error(" Failed to read the property file! ", e1);
            }

        //TODO Сделать обработку разных исключений
        /*} catch (Exception e) {
        LOG.error(" Application error! ", e);
        }*/
    }
}
