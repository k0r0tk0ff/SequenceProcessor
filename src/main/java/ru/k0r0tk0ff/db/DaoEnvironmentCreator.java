package ru.k0r0tk0ff.db;

/**
 * Create table environment in DB for DAO
 */
public interface DaoEnvironmentCreator {

    void truncateTable();

    void createTableInDbIfTableNotExist();

}
