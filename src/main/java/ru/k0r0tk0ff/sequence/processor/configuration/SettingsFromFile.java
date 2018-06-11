package ru.k0r0tk0ff.sequence.processor.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.util.Properties;

/**
 * Created by k0r0tk0ff.
 * Use for load properties from file on local filesystem.
 */
public class SettingsFromFile implements Settings {

    private BufferedReader readerForSettingsInFile;

    public SettingsFromFile(BufferedReader readerForSettingsInFile) {
        this.readerForSettingsInFile = readerForSettingsInFile;
    }

    private Properties properties;
    private static final Logger LOG = LoggerFactory.getLogger(SettingsFromFile.class);

    public void load() throws Exception {
        properties = new Properties();

        properties.load(readerForSettingsInFile);

        LOG.debug("Load properties from file success ..............");
        LOG.debug("key = jdbc.url       value = " + properties.getProperty("jdbc.url"));
        LOG.debug("key = jdbc.login     value = " + properties.getProperty("jdbc.login"));
        LOG.debug("key = jdbc.password  value = " + properties.getProperty("jdbc.password"));
        LOG.debug("key = n              value = " + properties.getProperty("n"));
        LOG.debug(".......................................................................");
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }
}
