package org.ferris.resiste.console.sql;

import java.io.File;
import java.util.Optional;
import java.util.StringJoiner;
import javax.enterprise.inject.Vetoed;
import org.ferris.resiste.console.data.DataDirectory;
import org.ferris.resiste.console.util.properties.PropertyValueDecoder;
import org.ferris.resiste.console.util.properties.PropertyValues;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class SqlProperties extends PropertyValues {

    private static final long serialVersionUID = 3890234537753408843L;

    protected DataDirectory dataDirectory;

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        sj.add(String.format("url=%s", getUrl()));
        sj.add(String.format("username=%s", getUsername()));
        sj.add(String.format("schema=%s", getSchema()));
        return sj.toString();
    }

    public SqlProperties(File file) {
        super(file);
    }

    public SqlProperties(File file, PropertyValueDecoder decoder, DataDirectory dataDirectory) {
        super(file, decoder);
        this.dataDirectory = dataDirectory;
    }

    public String getSchema() {
        Optional<String> schema
            = Optional.ofNullable(super.getProperty("schema"));
        return schema.orElseGet(() -> "APP");
    }

    public String getPassword() {
        Optional<String> password
            = Optional.ofNullable(super.getProperty("password"));
        return password.orElseGet(() -> "x4A03HZ7ZV*lzB%")
        ;
    }

    public String getUsername() {
        Optional<String> username
            = Optional.ofNullable(super.getProperty("username"));
        return username.orElseGet(() -> "resiste_standalone")
        ;
    }

    public String getUrl() {
        Optional<String> url
            = Optional.ofNullable(super.getProperty("url"));
        return url.orElseGet(() -> {
            try {
                return String.format(
                      "jdbc:derby:%s/resiste"
                    , dataDirectory.getCanonicalPath()
                );
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        });
    }
}
