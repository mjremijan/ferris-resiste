package org.ferris.resiste.console.security;

import jakarta.enterprise.inject.Vetoed;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class SecureProperties extends Properties {

    private static final long serialVersionUID = 1972374587234985775L;

    protected Optional<Rsa> rsa;

    public SecureProperties(File file, Optional<Rsa> rsa) {
        super();
        init(rsa);
        init(file);
    }

    private void init(Optional<Rsa> rsa) {
        this.rsa = rsa;
    }

    private void init(File file) {
        if (file == null) return;

        try (InputStream is = new FileInputStream(file);) {
            super.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getProperty(String key) {
        final String value = super.getProperty(key);

        if (value == null) {
            return value;
        }

        return
            rsa.map(r -> {
                if (value.startsWith("rsa{") && value.charAt(value.length()-1) == '}') {
                    // remove } from the end and rsa{ from beginning
                    String parsed = value.substring(0, value.length()-1).substring(4);
                    // decrypt
                    return r.base64DecodeAndDecrypt(parsed);
                } else {
                    // by returning null, the .orElseGet() will then be called.
                    return null;
                }
            }).orElseGet(() -> value)
        ;
    }

    /**
     * Similar to #setPropery() but the value is set iff a value doesn't already exist.
     *
     * @param key - The key to be placed into this property list.
     * @param value - The value corresponding to <code>key</code>.
     *
     * @return The previous value of the specified key in this property list, or null if it did not have one.
     */
    public Object setPropertyIfNull(String key, String value) {
        return setProperty(
             key
            ,Optional.ofNullable(getProperty(key)).orElseGet(() -> value)
        );
    }

}
