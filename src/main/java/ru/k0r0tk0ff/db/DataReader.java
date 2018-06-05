package ru.k0r0tk0ff.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DataReader {
    ResultSet getData() throws Exception;
}
