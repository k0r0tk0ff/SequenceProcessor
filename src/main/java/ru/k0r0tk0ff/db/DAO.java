package ru.k0r0tk0ff.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DAO {

    void insertDataToDb(int n);

    ResultSet getData() throws Exception;
}
