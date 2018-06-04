package ru.k0r0tk0ff.db;

import java.sql.Connection;
import java.sql.ResultSet;

public interface DataReader {

    ResultSet getDataFromDb();
}
