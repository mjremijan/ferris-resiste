package org.ferris.resiste.console.security;

import jakarta.enterprise.inject.Vetoed;
import jakarta.inject.Inject;
import java.io.File;
import org.ferris.resiste.console.application.ApplicationDirectory;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class SecurityDirectory extends File {

    private static final long serialVersionUID = 7491901906021288631L;

    @Inject
    public SecurityDirectory(ApplicationDirectory appdir) {
        super(appdir, "security");
    }

}
