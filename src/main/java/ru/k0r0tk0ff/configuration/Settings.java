package ru.k0r0tk0ff.configuration;

import java.io.InputStream;

/**
 * Created by k0r0tk0ff
 * Interface describe data reading from resources
 */
public interface Settings {

    void load() throws Exception;

    String getValue(String key);
}
