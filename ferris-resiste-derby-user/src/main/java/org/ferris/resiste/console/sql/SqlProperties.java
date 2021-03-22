package org.ferris.resiste.console.sql;

import java.io.File;
import java.io.FileInputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.StringJoiner;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class SqlProperties extends Properties {

    SqlProperties(File sqlPropertiesFile) {
        super();
        try {
            load(new FileInputStream(sqlPropertiesFile));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * Similar to #setPropery() but the value is set iff a value doesn't already exist.
     */
    public Object setPropertyIfNull(String key, String value) {
        return setProperty(
             key
            ,Optional.ofNullable(getProperty(key)).orElseGet(() -> value)
        );
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        sj.add(String.format("url=%s", getUrl()));
        sj.add(String.format("username=%s", getUsername()));
        sj.add(String.format("schema=%s", getSchema()));
        return sj.toString();
    }

    public String getSchema() {
        return getProperty("schema");
    }

    public String getPassword() {
        return getProperty("password");
    }

    public String getUsername() {
        return getProperty("username");
    }

    public String getUrl() {
        return getProperty("url");
    }
}
