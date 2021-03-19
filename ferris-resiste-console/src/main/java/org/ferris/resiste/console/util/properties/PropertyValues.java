package org.ferris.resiste.console.util.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import javax.enterprise.inject.Vetoed;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
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
        System.out.printf("key = %s%n", key);
        String val = super.getProperty(key);
        System.out.printf("val = %s%n", (val == null) ? "~NULL~" : val);

        Optional<PropertyValueDecoder> responsible
            = decoder.responsible(val);
        System.out.printf("Responsible decoder = %s %n", (responsible.isPresent()) ? responsible.get() : "~NOT PRESENT~" );

        Optional<String> map
            = responsible.map(d -> d.decode(val));
        System.out.printf("Mapped to Strng = %s %n", (map.isPresent()) ? map.get() : "~NOT PRESENT~" );

        System.out.printf("decoded = %n");
        return decoder
            .responsible(val)
            .map(d -> d.decode(val))
            .orElseThrow(() -> new RuntimeException(String.format("No decoder found for \"%s\"=\"%s\"", key, val)))
        ;
    }
}
