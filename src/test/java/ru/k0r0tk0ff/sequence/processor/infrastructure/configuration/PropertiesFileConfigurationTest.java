package ru.k0r0tk0ff.sequence.processor.infrastructure.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

//@RunWith(MockitoJUnitRunner.class)
public class PropertiesFileConfigurationTest {
    @Test
    public void load() throws Exception {
        final String PATH_TO_PROPERTIES_FILE = "parameters.properties";

        Properties properties;
        properties = new Properties();
        BufferedReader reader;
        reader = new BufferedReader(
                Files.newBufferedReader(
                        Paths.get(PATH_TO_PROPERTIES_FILE),
                        Charset.forName("UTF-8"))
        );
        properties.load(reader);

        assertThat(properties.getProperty("jdbc.url"),
                is("jdbc:postgresql://127.0.0.1:5432/postgres"));
    }


    @Test
    public void getInstance() throws Exception {

    }

    @Test
    public void getValue() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("jdbc.url", "jdbc:postgresql://127.0.0.1:5432/postgres");

        assertThat(properties.getProperty("jdbc.url"), is ("jdbc:postgresql://127.0.0.1:5432/postgres"));
    }
}