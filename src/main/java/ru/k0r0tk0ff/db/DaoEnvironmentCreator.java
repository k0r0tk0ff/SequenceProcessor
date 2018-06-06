package ru.k0r0tk0ff.db;

/**
 * Create table environment in DB for Dao
 */
public interface DaoEnvironmentCreator {

    void truncateTable();

    void createTableInDbIfTableNotExist();

}
