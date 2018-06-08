package ru.k0r0tk0ff.starter;

import java.sql.Connection;
import java.util.Collection;

public interface Starter {
    Connection createConnectionToDb();

    void setN(int n);

    void setUrl(String url);

    void setLogin(String login);

    void setPassword(String password);

    void uploadDataToTable(Connection connection) throws Exception;

    Collection<String> getDataFromDb(Connection connection) throws Exception;

    void generateXml(Collection<String> list) throws Exception;

    void xsltTransform(String s, String s1) throws Exception;

    public Collection<Integer> getDataFromResource(String resource) throws Exception;

    Integer sumOfElements(Collection<Integer> data);
}
