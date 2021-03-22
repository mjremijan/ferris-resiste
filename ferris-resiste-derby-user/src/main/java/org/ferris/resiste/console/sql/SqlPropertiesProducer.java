package org.ferris.resiste.console.sql;

import java.io.File;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.ferris.resiste.console.data.DataDirectory;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class SqlPropertiesProducer {

    @Inject
    protected SqlPropertiesFile sqlPropertiesFile;

    @Inject
    protected DataDirectory dataDirectory;

    protected SqlProperties p;

    @PostConstruct
    protected void postConstruct() {
        // create object
        p = new SqlProperties(sqlPropertiesFile);

        // verify derby
        String url = p.getProperty("url");
        if (url == null) {
            // location of database directory
            File dbdir = new File(dataDirectory, "resiste");
            url = String.format("jdbc:derby:%s", dbdir.getPath());
            p.setProperty("url", url);
        }

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
