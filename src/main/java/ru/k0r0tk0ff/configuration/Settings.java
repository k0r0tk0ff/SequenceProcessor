package ru.k0r0tk0ff.configuration;

/**
 * Created by k0r0tk0ff
 * Interface describe data reading from resources
 */
public interface Settings {

    void loadConfigurationSettings();

    String getValue(String key);
}
