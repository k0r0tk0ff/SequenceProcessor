package ru.k0r0tk0ff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.k0r0tk0ff.configuration.Settings;
import ru.k0r0tk0ff.configuration.SettingsFromFile;
import ru.k0r0tk0ff.dao.PostgresSequenceDao;
import ru.k0r0tk0ff.dao.SequenceDao;
import ru.k0r0tk0ff.db.ConnectionDaoImpl;
import ru.k0r0tk0ff.parser.SequenceParser;
import ru.k0r0tk0ff.parser.XmlSequenceParser;
import ru.k0r0tk0ff.sequence.PostgresSequenceEnvironment;
import ru.k0r0tk0ff.sequence.SequenceEnvironment;
import ru.k0r0tk0ff.xml.SequenceWriter;
import ru.k0r0tk0ff.xml.XmlSequenceWriter;
import ru.k0r0tk0ff.xslt.XsltTransformer;
import ru.k0r0tk0ff.xslt.XsltTransformerImpl;

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
 *
 * @since +1.8
 */
public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private int n;
    private String url;
    private String login;
    private String password;
    private String propertiesFileName;
    private String xsltFileName;
    private String xmlFileName;

    private String xmlFileNameWithRawData;
    private String xmlFileNameForParse;
    private BufferedReader inputForProperties = null;

    public static void main(String[] args) {
        Integer sum;
        Connection connection = null;
        SequenceEnvironment seqEnv;
        SequenceDao seqDaoImpl;

        Main main = new Main();
        try {
            main.loadProperties();
            connection = main.getConnection();

            seqEnv = new PostgresSequenceEnvironment(connection);
            seqDaoImpl = new PostgresSequenceDao(connection, seqEnv);

            main.putSequenceToStorage(seqDaoImpl);
            main.getAndWriteSequenceToFile(
                    main.getXmlFileName(),
                    main.getXmlFileNameWithRawData(),
                    seqDaoImpl.get());
            main.transformXmlFileWithUseXsltAndSave(main.getXmlFileName(),
                    main.getXsltFileName(),
                    main.getXmlFileNameForParse());
            sum = main.sumOfElements(main.getParsedSequence(main.getXmlFileNameForParse()));

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

    private void loadProperties() throws Exception {
        setPropertiesFileName("parameters.properties");
        setXmlFileNameWithRawData("0.xml");
        setXmlFileName("1.xml");
        setXmlFileNameForParse("2.xml");
        setXsltFileName("Transform.xslt");

        setInputForProperties(
                Files.newBufferedReader(
                        Paths.get(getPropertiesFileName()),
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
        return data.stream().reduce((s1,s2) -> s1+s2).orElse(0);
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

    private String getPropertiesFileName() {
        return propertiesFileName;
    }

    private void setPropertiesFileName(String propertiesFileName) {
        this.propertiesFileName = propertiesFileName;
    }

    private String getXsltFileName() {
        return xsltFileName;
    }

    private void setXsltFileName(String xsltFileName) {
        this.xsltFileName = xsltFileName;
    }

    private String getXmlFileName() {
        return xmlFileName;
    }

    private void setXmlFileName(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    private String getXmlFileNameForParse() {
        return xmlFileNameForParse;
    }

    private void setXmlFileNameForParse(String xmlFileNameForParse) {
        this.xmlFileNameForParse = xmlFileNameForParse;
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

    private String getXmlFileNameWithRawData() {
        return xmlFileNameWithRawData;
    }

    private void setXmlFileNameWithRawData(String xmlFileNameWithRawData) {
        this.xmlFileNameWithRawData = xmlFileNameWithRawData;
    }
}
