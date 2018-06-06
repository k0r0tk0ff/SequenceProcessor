package ru.k0r0tk0ff.dao;

import java.util.Collection;

public interface Dao {

    void insertData(int n) throws Exception;

    Collection<String> getData() throws Exception;

}
