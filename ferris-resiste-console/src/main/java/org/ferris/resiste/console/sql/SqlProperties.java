package org.ferris.resiste.console.sql;

import java.io.File;
import java.util.Optional;
import java.util.StringJoiner;
import javax.enterprise.inject.Vetoed;
import org.ferris.resiste.console.security.Rsa;
import org.ferris.resiste.console.security.SecureProperties;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class SqlProperties extends SecureProperties {

    private static final long serialVersionUID = 3890234537753408843L;

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        sj.add(String.format("url=%s", getUrl()));
        sj.add(String.format("username=%s", getUsername()));
        sj.add(String.format("schema=%s", getSchema()));
        return sj.toString();
    }

    public SqlProperties(File file, Optional<Rsa> rsa) {
        super(file, rsa);
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
