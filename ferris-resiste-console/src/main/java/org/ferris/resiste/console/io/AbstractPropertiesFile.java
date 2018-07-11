package org.ferris.resiste.console.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class exists to provide common functionality to all the application
 * objects which are meant to extend {@link File}
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public abstract class AbstractPropertiesFile extends File {

    private static final long serialVersionUID = 182745237450703456L;

    /**
     * Pass to super constructor w/o error checking
     *
     * @param parent The parent, relative to the {@code relativePath} parameter
     * @param relativePath The relative path to the resource from the {@code parent}
     */
    public AbstractPropertiesFile(File parent, String relativePath) {
        super(parent, relativePath);
    }

    /**
     * Return a {@link Properties} object representation of this file.
     *
     * @return Return a {@link Properties} object, never return {@code null}
     * @throws RuntimeException Wraps any other exception which might be thrown
     */
    public Properties toProperties() {
        Properties props = new Properties();
        try (InputStream is = new FileInputStream(this);) {
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return props;
    }
}
