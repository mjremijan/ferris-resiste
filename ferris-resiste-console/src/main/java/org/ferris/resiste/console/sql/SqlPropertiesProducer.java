package org.ferris.resiste.console.sql;

import java.io.File;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.ferris.resiste.console.data.DataDirectory;
import org.ferris.resiste.console.security.Rsa;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class SqlPropertiesProducer {

    @Inject
    protected Logger log;

    @Inject
    protected SqlPropertiesFile sqlPropertiesFile;

    @Inject
    protected Optional<Rsa> rsa;

    @Inject
    protected DataDirectory dataDirectory;

    protected SqlProperties p;

    @PostConstruct
    protected void postConstruct() {
        // create object
        p = new SqlProperties(sqlPropertiesFile, rsa);

        // verify derby
        String url = p.getProperty("url");
        if (url == null) {
            // location of database directory
            File dbdir = new File(dataDirectory, "resiste");
            url = String.format("jdbc:derby:%s", dbdir.getPath());
            p.setProperty("url", url);

            // location of derby home
            System.setProperty("derby.system.home", dbdir.getParent());
        } else {
            // location of database
            // jdbc:derby:[subsubprotocol:][databaseName][;attribute=value]*
            int start = url.lastIndexOf("derby:") + "derby:".length();
            int end = url.indexOf(";");
            end = (end == -1) ? url.length() : end;
            String substr = url.substring(start, end);
            log.info(String.format("Database location substring = start=%d, end=%d: \"%s\"%n", start, end, substr));
            File dbloc = new File(substr);

            // location of derby home
            if (dbloc.canRead()) {
                System.setProperty("derby.system.home", dbloc.getParent());
            } else {
                // location of derby home
                System.setProperty("derby.system.home", dataDirectory.getPath());
            }
        }
        log.info(String.format("derby.system.home=\"%s\"", System.getProperty("derby.system.home")));

        // verify username
        p.setPropertyIfNull("username", "resiste_standalone");

        // verify password
        p.setPropertyIfNull("password", "x4A03HZ7ZV*lzB%");

        // verify schema
        p.setPropertyIfNull("schema", "APP");
    }

    @Produces
    public SqlProperties produceSqlProperties() {
        return p;
    }
}
