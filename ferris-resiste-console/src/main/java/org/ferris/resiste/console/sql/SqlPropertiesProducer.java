package org.ferris.resiste.console.sql;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.ferris.resiste.console.data.DataDirectory;
import org.ferris.resiste.console.encryption.Rsa;
import org.ferris.resiste.console.util.properties.PropertyValueDecoder;
import org.ferris.resiste.console.util.properties.PropertyValueDecoderForEcho;
import org.ferris.resiste.console.util.properties.PropertyValueDecoderForRsa;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class SqlPropertiesProducer {

    protected SqlProperties sqlProperties;

    // DataDirectory dataDirectory

    @Inject
    public SqlPropertiesProducer(SqlPropertiesFile sqlPropertiesFile, Optional<Rsa> rsa, DataDirectory dataDirectory) {
        if (rsa.isPresent()) {
            PropertyValueDecoder first = new PropertyValueDecoderForRsa(rsa.get());
            PropertyValueDecoder second = new PropertyValueDecoderForEcho();
            first.next(second);
            sqlProperties = new SqlProperties(sqlPropertiesFile, first, dataDirectory);
        } else {
            sqlProperties = new SqlProperties(sqlPropertiesFile);
        }
    }

    @Produces
    public SqlProperties produceSqlProperties() {
        return sqlProperties;
    }
}
