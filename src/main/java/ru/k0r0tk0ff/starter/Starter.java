package ru.k0r0tk0ff.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.k0r0tk0ff.db.*;
import ru.k0r0tk0ff.xml.XmlGenerator;
import ru.k0r0tk0ff.xml.XmlGeneratorImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *  Main class of program
 */
public class Starter {
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

    private static final Logger LOG  = LoggerFactory.getLogger(Starter.class);

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

    public void createTableIfNotExistAndFillData() {
        DataWriter dataWriter = new DataWriterImpl(connection);
        dataWriter.createTableInDb();
        dataWriter.truncateTable();
        dataWriter.insertDataToDb(getN());
    }

    private ResultSet getRawDataFromDb() throws Exception {
        ResultSet resultSet;

        DataReader dataReader = new DataReaderImpl(connection);

        resultSet = dataReader.getData();
        return resultSet;
    }

    public ArrayList<String> getDataFromDb() {
        ArrayList<String> list = new ArrayList<>();
        ResultSet resultSet;

        try {
            resultSet = getRawDataFromDb();

            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
        } catch (Exception e) {
            LOG.error(e.toString());
            LOG.error(".......................................................................");
        }

        if(LOG.isDebugEnabled()) {
            LOG.debug(" Get and convert data from db success ");
        }

        return list;
    }

    public void generateXml(ArrayList<String> list) {
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
