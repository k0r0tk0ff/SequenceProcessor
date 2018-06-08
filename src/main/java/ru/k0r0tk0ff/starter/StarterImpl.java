package ru.k0r0tk0ff.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.k0r0tk0ff.dao.SequenceDao;
import ru.k0r0tk0ff.dao.PostgresSequenceDao;
import ru.k0r0tk0ff.db.*;
import ru.k0r0tk0ff.parser.XmlSequenceParser;
import ru.k0r0tk0ff.parser.SequenceParser;
import ru.k0r0tk0ff.xml.SequenceWriter;
import ru.k0r0tk0ff.xml.XmlSequenceWriter;
import ru.k0r0tk0ff.xslt.XsltTransformer;
import ru.k0r0tk0ff.xslt.XsltTransformerImpl;

import java.sql.Connection;
import java.util.Collection;

/**
 *  Main class of program
 */
public class StarterImpl implements Starter{
    private int n;
    private String url;
    private String login;
    private String password;
    private Connection connection;
    private ConnectionDao dataResource = null;

    public Connection createConnectionToDb() {
        this.dataResource = new ConnectionDaoImpl(
                getUrl(),
                getLogin(),
                getPassword()
        );

        return dataResource.getConnection();
    }

    private static final Logger LOG  = LoggerFactory.getLogger(StarterImpl.class);

    private Connection getConnection(ConnectionDao dataResource) {
        return dataResource.getConnection();
    }

    //Getters and Setters for class fields
    private int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    private String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    private String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void createTableIfNotExist(Connection connection) {
        SequenceEnvironment daoEnvCreatorImpl = new PostgresSequenceEnvironment(connection);
        daoEnvCreatorImpl.createSequenceInStorage();
        daoEnvCreatorImpl.clearSequence();
    }

    public void uploadDataToTable(Connection connection) throws Exception{
        SequenceDao sequenceDaoImpl = new PostgresSequenceDao(connection);
        sequenceDaoImpl.put(getN());
    }

    public Collection<String> getDataFromDb(Connection connection) throws Exception{
        Collection<String> list = null;
        SequenceDao sequenceDao = new PostgresSequenceDao(connection);

        list = sequenceDao.get();

        if(LOG.isDebugEnabled()) {
            LOG.debug(" Get and convert data from db success ");
        }
        return list;
    }

    public void generateXml(Collection<String> list) throws Exception {
        SequenceWriter sequenceWriter = new XmlSequenceWriter();
        sequenceWriter.write(list);
        if (LOG.isDebugEnabled()) {
            LOG.debug(" Generate XML success");
        }
    }

    public void xsltTransform(String xmlFileName, String xsltFileName) throws Exception{
        XsltTransformer transformer = new XsltTransformerImpl();
        transformer.transform("1.xml", "Transform.xslt");
        if (LOG.isDebugEnabled()) {
            LOG.debug(" Transform XML with use xslt success");
        }
    }

    public Collection<Integer> getDataFromResource(String resource) throws Exception{
        SequenceParser parser = new XmlSequenceParser();
        Collection<Integer> parsedData = null;

            parsedData = parser.parser(resource);

            if (LOG.isDebugEnabled()) {
                LOG.debug(" Parse file " + resource + " success");
            }

        return parsedData;
    }


    public Integer sumOfElements(Collection<Integer> data) {
        return data.stream().reduce((s1,s2) -> s1+s2).orElse(0);
    }
}
