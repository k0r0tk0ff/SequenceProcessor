package ru.k0r0tk0ff.starter;

import java.util.ArrayList;

public interface Starter {
    void createConnectionToDb();

    void closeConnection();

    void setN(int n);

    void setUrl(String url);

    void setLogin(String login);

    void setPassword(String password);

    void createTableIfNotExist();

    void uploadDataToTable();

    ArrayList<String> getDataFromDb();

    void generateXml(ArrayList<String> list);
}
