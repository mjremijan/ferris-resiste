package org.ferris.resiste.console.sql;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class SqlTool {

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
}
