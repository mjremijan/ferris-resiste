package org.ferris.resiste.console.sql;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.DriverManager;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class SqlConnectionProducer {

    private SqlConnection sqlConnection;

    @Inject
    protected Logger log;

    @Inject
    protected SqlProperties properties;

    @PostConstruct
    protected void postConstruct() {
        log.info(String.format("Create connection to database %s", properties));
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                properties.getUrl(), properties.getUsername(), properties.getPassword()
            );
        } catch (Exception e) {
            throw new RuntimeException(
                String.format("Problem connecting to database \"%s\", \"%s\", \"%s\""
                    , properties.getUrl(), properties.getUsername(), properties.getPassword()),
                 e
            );
        }

        try {
            conn.setSchema(properties.getSchema());
        } catch (Exception e) {
            throw new RuntimeException(
                String.format("Problem setting database schema to \"%s\"",properties.getSchema()), e
            );
        }

        sqlConnection = new SqlConnection(conn);
    }

    @Produces
    protected SqlConnection produceSqlConnection() {
        return sqlConnection;
    }
}
