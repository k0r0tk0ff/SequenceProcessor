package ru.k0r0tk0ff.starter;

import ru.k0r0tk0ff.db.*;

import java.sql.Connection;
import java.sql.ResultSet;

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

    public void initializeDataResources() {
        this.dataResource = new DataResourceImpl(
                getUrl(),
                getLogin(),
                getPassword()
        );

        this.connection = dataResource.getConnection();
    }

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

    public ResultSet getDataFromDb() {
        DataReader dataReader = new DataReaderImpl(connection);
        ResultSet resultSet;
        resultSet = dataReader.getDataFromDb();
        return resultSet;
    }
}
