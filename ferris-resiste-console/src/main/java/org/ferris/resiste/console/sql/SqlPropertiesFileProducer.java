package org.ferris.resiste.console.sql;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.ferris.resiste.console.conf.ConfDirectory;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class SqlPropertiesFileProducer {

    protected SqlPropertiesFile sqlPropertiesFile;

    @Inject
    public SqlPropertiesFileProducer(ConfDirectory confDirectory) {
        sqlPropertiesFile = new SqlPropertiesFile(confDirectory);
    }

    @Produces
    public SqlPropertiesFile produceSqlPropertiesFile() {
        return sqlPropertiesFile;
    }
}
