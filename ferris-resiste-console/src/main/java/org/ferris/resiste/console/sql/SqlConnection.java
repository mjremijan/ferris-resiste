package org.ferris.resiste.console.sql;

import jakarta.enterprise.inject.Vetoed;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class SqlConnection {

    private Connection conn;

    SqlConnection(Connection conn) {
        this.conn = conn;
    }

    public Connection getConnection() {
        return conn;
    }

    public void close(Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Throwable t) {}
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Throwable t) {}
        }
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }
    
    public PreparedStatement prepareUpdatableStatement(String sql) throws SQLException {
        return conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
    }
}
