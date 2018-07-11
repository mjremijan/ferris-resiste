package org.ferris.resiste.console.sql;

import java.util.Optional;
import java.util.StringJoiner;
import javax.inject.Inject;
import org.ferris.resiste.console.conf.ConfDirectory;
import org.ferris.resiste.console.data.DataDirectory;
import org.ferris.resiste.console.io.AbstractPropertiesFile;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class SqlProperties extends AbstractPropertiesFile {

    private static final long serialVersionUID = 375890234508843L;

    @Inject
    protected DataDirectory dataDirectory;

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        sj.add(String.format("url=%s", getUrl()));
        sj.add(String.format("username=%s", getUsername()));
        sj.add(String.format("schema=%s", getSchema()));
        return sj.toString();
    }

    @Inject
    public SqlProperties(ConfDirectory confdir) {
        super(confdir, String.format("db.properties"));
    }

    public String getSchema() {
        Optional<String> schema
            = Optional.ofNullable(super.toProperties().getProperty("schema"));
        return schema.orElseGet(() -> "APP");
    }

    public String getPassword() {
        Optional<String> password
            = Optional.ofNullable(super.toProperties().getProperty("password"));
        return password.orElseGet(() -> "x4A03HZ7ZV*lzB%");
    }

    public String getUsername() {
        Optional<String> username
            = Optional.ofNullable(super.toProperties().getProperty("username"));
        return username.orElseGet(() -> "resiste_standalone");
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
