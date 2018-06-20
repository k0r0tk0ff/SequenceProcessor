package ru.k0r0tk0ff.sequence.processor.infrastructure.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class PropertiesFileConfiguration  implements Configuration {
    private static final Logger LOG = LoggerFactory.getLogger(PropertiesFileConfiguration.class);
    private static PropertiesFileConfiguration propertiesFileConfiguration;
    private Properties properties;

    private PropertiesFileConfiguration(Properties properties) throws IOException {
        this.properties = properties;
    }

    public static void load(String pathToFileWithProperties) throws IOException {
        if (propertiesFileConfiguration == null) {
            File file = new File(pathToFileWithProperties);
            if (!file.exists()) {
                String errorMessage = "File \"" + pathToFileWithProperties + "\" not exists";
                LOG.error(errorMessage);
                throw new IOException(errorMessage);
            }
            try (InputStream inputStream = new FileInputStream(file)) {
                Properties localProperties = new Properties();
                localProperties.load(inputStream);
                LOG.debug(">>> Load properties from file success");
                LOG.debug(" key = jdbc.url       value = " + localProperties.getProperty("jdbc.url"));
                LOG.debug(" key = jdbc.login     value = " + localProperties.getProperty("jdbc.login"));
                LOG.debug(" key = jdbc.password  value = " + localProperties.getProperty("jdbc.password"));
                propertiesFileConfiguration = new PropertiesFileConfiguration(localProperties);
            } catch (IOException e) {
                throw new IOException(e);
            }
        }
    }

    public static  Configuration getInstance() {
        if (propertiesFileConfiguration == null) {
            throw new IllegalArgumentException("Use method \"load\" for instance before use");
        }
        return propertiesFileConfiguration;
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }
}
