package ru.k0r0tk0ff.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Created by k0r0tk0ff.
 * Use for load properties from file on local filesystem.
 */
public class SettingsFromFile implements Settings {

    private BufferedReader reader;
    private String fileName;

    public SettingsFromFile(String fileName, BufferedReader reader) {
        this.fileName = fileName;
        this.reader = reader;
    }

    private Properties properties;
    private static final Logger LOG = LoggerFactory.getLogger(SettingsFromFile.class);

    //For load from file in "resources" directory
    //public void load(InputStream input) throws Exception {

    public void load() throws Exception {
        properties = new Properties();

        properties.load(reader);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Load properties from file success ..............");
            LOG.debug("key = jdbc.url       value = " + properties.getProperty("jdbc.url"));
            LOG.debug("key = jdbc.login     value = " + properties.getProperty("jdbc.login"));
            LOG.debug("key = jdbc.password  value = " + properties.getProperty("jdbc.password"));
            LOG.debug("key = n              value = " + properties.getProperty("n"));
            LOG.debug(".......................................................................");
        }
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }
}
