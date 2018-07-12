package org.ferris.resiste.console.sql;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.ferris.resiste.console.conf.*;
import org.ferris.resiste.console.data.DataDirectory;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class SqlPropertiesProducer {

    protected SqlProperties sqlProperties;

    @Inject
    public SqlPropertiesProducer(ConfDirectory confDirectory, DataDirectory dataDirectory) {
        sqlProperties = new SqlProperties(confDirectory, dataDirectory);
    }

    @Produces
    public SqlProperties produceSqlProperties() {
        return sqlProperties;
    }
}
