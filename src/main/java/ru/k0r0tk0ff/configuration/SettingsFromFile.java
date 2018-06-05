package ru.k0r0tk0ff.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by k0r0tk0ff.
 * Use for load properties from file on local filesystem.
 */
public class SettingsFromFile implements Settings {

    private static final Logger LOG  = LoggerFactory.getLogger(SettingsFromFile.class);

    private Properties properties;

    @Override
    public void load() {
        properties = new Properties();
        InputStream input = this.getClass().getClassLoader().getResourceAsStream("parameters.properties");

        try {
            properties.load(input);
        } catch (IOException e) {
            LOG.error(e.toString());
            LOG.error(".......................................................................");
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    LOG.error(e.toString());
                    LOG.error(".......................................................................");
                }

                if(LOG.isDebugEnabled()) {
                    LOG.debug("Load properties from file success ..............");
                    LOG.debug("key = jdbc.url       value = " + properties.getProperty("jdbc.url"));
                    LOG.debug("key = jdbc.login     value = " + properties.getProperty("jdbc.login"));
                    LOG.debug("key = jdbc.password  value = " + properties.getProperty("jdbc.password"));
                    LOG.debug("key = n              value = " + properties.getProperty("n"));
                    LOG.debug(".......................................................................");
                }
            }
        }
    }

    @Override
    public String getValue(String key) {
        return properties.getProperty(key);
    }
}
