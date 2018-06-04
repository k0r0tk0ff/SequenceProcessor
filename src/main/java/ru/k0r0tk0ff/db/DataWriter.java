package ru.k0r0tk0ff.db;

import java.sql.Connection;

public interface DataWriter {

    void dropDataInDb();

    void createTableInDb();

    void insertDataToDb(int n);
}
