package ru.k0r0tk0ff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.k0r0tk0ff.sequence.processor.infrastructure.configuration.Configuration;
import ru.k0r0tk0ff.sequence.processor.infrastructure.configuration.PropertiesFileConfiguration;
import ru.k0r0tk0ff.sequence.processor.infrastructure.dao.PostgresSequenceDao;
import ru.k0r0tk0ff.sequence.processor.infrastructure.dao.SequenceDaoException;
import ru.k0r0tk0ff.sequence.processor.infrastructure.data.source.DbDataSource;
import ru.k0r0tk0ff.sequence.processor.infrastructure.data.source.DataSourceException;
import ru.k0r0tk0ff.sequence.processor.infrastructure.writer.WriterException;
import ru.k0r0tk0ff.sequence.processor.service.SequenceProcessor;
import ru.k0r0tk0ff.sequence.processor.service.XmlSequenceProcessor;
import ru.k0r0tk0ff.sequence.processor.infrastructure.dao.environment.PostgresSequenceEnvironment;
import ru.k0r0tk0ff.sequence.processor.infrastructure.writer.RawXmlSequenceWriter;
import ru.k0r0tk0ff.sequence.processor.utils.UtilsException;
import ru.k0r0tk0ff.sequence.processor.utils.input.sequense.parameters.ConsoleInput;
import ru.k0r0tk0ff.sequence.processor.utils.input.sequense.parameters.InputSequenceParameters;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private static final String PATH_TO_PROPERTIES_FILE = "parameters.properties";
    private static final String PATH_TO_XML_FILE_WITH_RAW_DATA = "0.xml";
    private static final String PATH_TO_XML_FILE = "1.xml";
    private static final String PATH_TO_XML_FILE_FOR_PARSE = "2.xml";
    private static final String PATH_TO_XSLT_FILE = "Transform.xslt";

    public static void main(String[] args) {
        final String dataSourceErrorMessage = " Failed to work with package \"datasource\" ! ";
        Integer seqMaxValue;
        InputSequenceParameters inputSequenceParameters = new ConsoleInput();
        seqMaxValue = inputSequenceParameters.getMaxValue();

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
            } catch (SQLException e) {
                LOG.error(dataSourceErrorMessage, e);
            }
        } catch (DataSourceException e1) {
            LOG.error(dataSourceErrorMessage, e1);
        } catch (UtilsException e2) {
            LOG.error(" Failed to work with package \"utils\" ! ", e2);
        } catch (WriterException e3) {
            LOG.error(" Failed to work with package \"writer\"  ! ", e3);
        } catch (SequenceDaoException e4) {
            LOG.error(" Failed to work with package \"dao\" ! ", e4);
        } catch (IOException e5) {
            LOG.error(" Failed to read the property file! ", e5);
        }
    }
}
