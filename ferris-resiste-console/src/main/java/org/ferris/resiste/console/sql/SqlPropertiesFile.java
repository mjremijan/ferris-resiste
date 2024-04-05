package org.ferris.resiste.console.sql;

import jakarta.enterprise.inject.Vetoed;
import java.io.File;

/**
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class SqlPropertiesFile extends File {

    private static final long serialVersionUID = 182745237455451276L;

    /**
     * Pass to super then verify access.
     *
     * @param parent The parent, relative to the {@code relativePath} parameter
     */
    public SqlPropertiesFile(File parent) {
        super(parent, "db.properties");
        if (!canRead()) {
            throw new RuntimeException(String.format("Unable to read sql properties file \"%s\"", getPath()));
        }
    }
}
