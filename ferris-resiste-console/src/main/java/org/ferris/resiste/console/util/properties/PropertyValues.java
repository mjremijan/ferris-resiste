package org.ferris.resiste.console.util.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class PropertyValues extends Properties {

    private static final long serialVersionUID = 1972374587234985775L;

    protected PropertyValueDecoder decoder;

    public PropertyValues(File file) {
        this(file, new PropertyValueDecoderForEcho());
    }

    public PropertyValues(File file, PropertyValueDecoder decoder) {
        super();
        init(decoder);
        init(file);
    }

    private void init(PropertyValueDecoder decoder) {
        if (decoder == null) {
            throw new IllegalArgumentException("\"decoder\" parameter is null");
        }
        this.decoder = decoder;
    }

    private void init(File file) {
        try (InputStream is = new FileInputStream(file);) {
            super.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getProperty(String key) {
        String val = super.getProperty(key);
        return decoder
            .responsible(val)
            .map(d -> d.decode(val))
            .orElseThrow(() -> new RuntimeException(String.format("No decoder found for \"%s\"=\"%s\"", key, val)))
        ;
    }
}
