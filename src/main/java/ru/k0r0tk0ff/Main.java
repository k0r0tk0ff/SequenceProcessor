package ru.k0r0tk0ff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.k0r0tk0ff.sequence.processor.infrastructure.configuration.Settings;
import ru.k0r0tk0ff.sequence.processor.infrastructure.configuration.SettingsFromFile;
import ru.k0r0tk0ff.sequence.processor.infrastructure.dao.PostgresSequenceDao;
import ru.k0r0tk0ff.sequence.processor.infrastructure.dao.SequenceDao;
import ru.k0r0tk0ff.sequence.processor.infrastructure.db.ConnectionDaoImpl;
import ru.k0r0tk0ff.sequence.processor.utils.xml.parser.SequenceParser;
import ru.k0r0tk0ff.sequence.processor.utils.xml.parser.XmlSequenceParser;
import ru.k0r0tk0ff.sequence.processor.infrastructure.dao.environment.PostgresSequenceEnvironment;
import ru.k0r0tk0ff.sequence.processor.infrastructure.dao.environment.SequenceEnvironment;
import ru.k0r0tk0ff.sequence.processor.infrastructure.writer.SequenceWriter;
import ru.k0r0tk0ff.sequence.processor.infrastructure.writer.XmlSequenceWriter;
import ru.k0r0tk0ff.sequence.processor.utils.xslt.transformer.XsltTransformer;
import ru.k0r0tk0ff.sequence.processor.utils.xslt.transformer.XsltTransformerImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

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

    /*
     * Max element's value, and count of elements in environment
     */
    private int n;

    private String url;
    private String login;
    private String password;

    private BufferedReader inputForProperties = null;

    public static void main(String[] args) {
        Integer sum;
        Connection connection = null;
        SequenceEnvironment seqEnv;
        SequenceDao seqDaoImpl;

        Main main = new Main();
        try {
            main.initialize();
            connection = main.getConnection();

            seqEnv = new PostgresSequenceEnvironment(connection);
            seqDaoImpl = new PostgresSequenceDao(connection, seqEnv);

            main.putSequenceToStorage(seqDaoImpl);
            main.getAndWriteSequenceToFile(
                    XML_FILE_NAME,
                    XML_FILE_NAME_WITH_RAW_DATA,
                    seqDaoImpl.get());
            main.transformXmlFileWithUseXsltAndSave(XML_FILE_NAME,
                    XSLT_FILE_NAME,
                    XML_FILE_NAME_FOR_PARSE);
            sum = main.sumOfElements(main.getParsedSequence(XML_FILE_NAME_FOR_PARSE));

            System.out.println("Sum = " + sum);
        } catch (Exception e1) {
            LOG.error(".......................................................................");
            LOG.error("Application error!", e1);
        } finally {
            if (connection != null)
                try {
                    connection.close();
                    LOG.debug(" Close connection success");
                } catch (SQLException e2) {
                    LOG.error(".......................................................................");
                    LOG.error("Application error!", e2);
            }
            if (main.inputForProperties != null) {
                try {
                    main.inputForProperties.close();
                } catch (IOException e3) {
                    LOG.error(".......................................................................");
                    LOG.error("Application error!", e3);
                }
            }
        }
    }

    private void initialize() throws Exception {
        setInputForProperties(
                Files.newBufferedReader(
                        Paths.get(PROPERTIES_FILE_NAME),
                Charset.forName("UTF-8")));
        Settings settings = new SettingsFromFile(getInputForProperties());
        settings.load();

        setN(Integer.parseInt(settings.getValue("n")));
        setUrl(settings.getValue("jdbc.url"));
        setLogin(settings.getValue("jdbc.login"));
        setPassword(settings.getValue("jdbc.password"));
    }

    private Connection getConnection() throws Exception{
        ConnectionDaoImpl connectionDao = new ConnectionDaoImpl(
                getUrl(),
                getLogin(),
                getPassword()
        );
        return connectionDao.getConnection();
    }

    private void putSequenceToStorage(SequenceDao seqDaoImpl) throws Exception {
        seqDaoImpl.put(getN());
    }

    private Integer sumOfElements(Collection<Integer> data) {
        return data.parallelStream().reduce((s1,s2) -> s1+s2).orElse(0);
    }

    private void getAndWriteSequenceToFile(
            String xmlFileName,
            String xmlFileNameWithRawData,
            Collection<String> sequence)
            throws Exception {
        SequenceWriter sequenceWriter = new XmlSequenceWriter(xmlFileName, xmlFileNameWithRawData);
        sequenceWriter.write(sequence);
    }

    private void transformXmlFileWithUseXsltAndSave(String xmlFileName,
                                                    String xsltFileName,
                                                    String resultXmlFileName)
            throws Exception {
        XsltTransformer transformer = new XsltTransformerImpl();
        transformer.transform(xmlFileName, xsltFileName, resultXmlFileName);
    }

    private Collection<Integer> getParsedSequence(String xmlFileNameForParse)
            throws Exception {
        SequenceParser parser = new XmlSequenceParser();
        return parser.doParse(xmlFileNameForParse);
    }

    private BufferedReader getInputForProperties() {
        return inputForProperties;
    }

    private void setInputForProperties(BufferedReader inputForProperties) {
        this.inputForProperties = inputForProperties;
    }

    private int getN() {
        return n;
    }

    private void setN(int n) {
        this.n = n;
    }

    private String getUrl() {
        return url;
    }

    private void setUrl(String url) {
        this.url = url;
    }

    private String getLogin() {
        return login;
    }

    private void setLogin(String login) {
        this.login = login;
    }

    private String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }
}
