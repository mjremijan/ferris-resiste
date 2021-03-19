package org.ferris.resiste.console.sql;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.ferris.resiste.console.data.DataDirectory;
import org.ferris.resiste.console.security.Rsa;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class SqlPropertiesProducer {

    protected SqlProperties p;

    @Inject
    public SqlPropertiesProducer(SqlPropertiesFile sqlPropertiesFile, Optional<Rsa> rsa, DataDirectory dataDirectory) {

        // create object
        p = new SqlProperties(sqlPropertiesFile, rsa);

        // verify url
        p.setPropertyIfNull("url", String.format("jdbc:derby:%s/resiste", dataDirectory.getPath()));

        // derby stuff
        System.setProperty("derby.system.home", )

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
