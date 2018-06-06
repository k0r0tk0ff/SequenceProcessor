package ru.k0r0tk0ff.starter;

import java.util.Collection;

public interface Starter {
    void createConnectionToDb();

    void closeConnection();

    void setN(int n);

    void setUrl(String url);

    void setLogin(String login);

    void setPassword(String password);

    void createTableIfNotExist();

    void uploadDataToTable();

    Collection<String> getDataFromDb();

    void generateXml(Collection<String> list);

    void xsltTransform(String s, String s1);
}
