package org.ferris.resiste.console.sql;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.ferris.resiste.console.conf.*;
import org.ferris.resiste.console.data.DataDirectory;
import org.ferris.resiste.console.rsa.Rsa4096;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class SqlPropertiesProducer {

    protected SqlProperties sqlProperties;

    @Inject
    public SqlPropertiesProducer(ConfDirectory confDirectory, DataDirectory dataDirectory, Rsa4096 rsa) {
        sqlProperties = new SqlProperties(confDirectory, dataDirectory, rsa);
    }

    @Produces
    public SqlProperties produceSqlProperties() {
        return sqlProperties;
    }
}
