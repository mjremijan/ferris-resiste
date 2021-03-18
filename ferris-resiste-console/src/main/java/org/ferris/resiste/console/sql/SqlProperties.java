package org.ferris.resiste.console.sql;

import java.util.Optional;
import java.util.StringJoiner;
import javax.enterprise.inject.Vetoed;
import org.ferris.resiste.console.conf.ConfDirectory;
import org.ferris.resiste.console.data.DataDirectory;
import org.ferris.resiste.console.io.AbstractPropertiesFile;
import org.ferris.resiste.console.encryption.RsaDecrypt;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class SqlProperties extends AbstractPropertiesFile {

    private static final long serialVersionUID = 375890234508843L;

    protected DataDirectory dataDirectory;
    protected RsaDecrypt rsa;

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        sj.add(String.format("url=%s", getUrl()));
        sj.add(String.format("username=%s", getUsername()));
        sj.add(String.format("schema=%s", getSchema()));
        return sj.toString();
    }

    public SqlProperties(ConfDirectory confDirectory, DataDirectory dataDirectory, RsaDecrypt rsa) {
        super(confDirectory, String.format("db.properties"));
        this.dataDirectory = dataDirectory;
        this.rsa = rsa;
    }

    public String getSchema() {
        Optional<String> schema
            = Optional.ofNullable(super.toProperties().getProperty("schema"));
        return schema.orElseGet(() -> "APP");
    }

    public String getPassword() {
        Optional<String> password
            = Optional.ofNullable(super.toProperties().getProperty("password"));
        return password
            .map( s -> rsa.getValue(s))
            .orElseGet(() -> "x4A03HZ7ZV*lzB%")
        ;
    }

    public String getUsername() {
        Optional<String> username
            = Optional.ofNullable(super.toProperties().getProperty("username"));
        return username
            .map( s -> rsa.getValue(s))
            .orElseGet(() -> "resiste_standalone")
        ;
    }

    public String getUrl() {
        Optional<String> url
            = Optional.ofNullable(super.toProperties().getProperty("url"));
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
