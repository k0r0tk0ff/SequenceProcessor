package ru.k0r0tk0ff.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.k0r0tk0ff.dao.Dao;
import ru.k0r0tk0ff.dao.DbDaoImpl;
import ru.k0r0tk0ff.db.*;
import ru.k0r0tk0ff.xml.XmlGenerator;
import ru.k0r0tk0ff.xml.XmlGeneratorImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
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
    private DataResource dataResource = null;

    public void createConnectionToDb() {
        this.dataResource = new DataResourceImpl(
                getUrl(),
                getLogin(),
                getPassword()
        );

        this.connection = dataResource.getConnection();
    }

    private static final Logger LOG  = LoggerFactory.getLogger(StarterImpl.class);

    private Connection getConnection(DataResource dataResource) {
        return dataResource.getConnection();
    }

    public void closeConnection() {
        this.dataResource.closeConnection(this.connection);
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

    public void createTableIfNotExist() {
        DaoEnvironmentCreator daoEnvCreatorImpl = new DaoEnvironmentCreatorImpl(connection);
        daoEnvCreatorImpl.createTableInDbIfTableNotExist();
        daoEnvCreatorImpl.truncateTable();

    }

    public void uploadDataToTable() {
        Dao daoImpl = new DbDaoImpl(connection);
        daoImpl.insertData(getN());
    }

    public Collection<String> getDataFromDb() {
        Collection<String> list = null;
        Dao dao = new DbDaoImpl(connection);

        try {
            list = dao.getData();
        } catch (Exception e) {
            LOG.error(e.toString());
            LOG.error(".......................................................................");
        }

        if(LOG.isDebugEnabled()) {
            LOG.debug(" Get and convert data from db success ");
        }

        return list;
    }

    public void generateXml(Collection<String> list) {
        XmlGenerator xmlGenerator = new XmlGeneratorImpl();
        try {
            xmlGenerator.generateXml(list);
        } catch (Exception e) {
            LOG.error(e.toString());
            LOG.error(".......................................................................");
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug(" Generate XML success");
        }
    }
}
