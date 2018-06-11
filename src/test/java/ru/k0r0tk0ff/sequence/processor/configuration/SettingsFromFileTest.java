package ru.k0r0tk0ff.sequence.processor.configuration;

import org.junit.Test;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

public class SettingsFromFileTest {
    @Test
    public void loadPropertiesFromFileAndGetValue() throws Exception {
        final String PROPERTIES_FILE_NAME = "parameters.properties";

        Properties properties;
        properties = new Properties();
        BufferedReader reader;
        reader = new BufferedReader(
                Files.newBufferedReader(
                        Paths.get(PROPERTIES_FILE_NAME),
                        Charset.forName("UTF-8"))
        );
        properties.load(reader);

        assertThat(properties.getProperty("jdbc.url"),
                is("jdbc:postgresql://127.0.0.1:5432/postgres"));
    }
}