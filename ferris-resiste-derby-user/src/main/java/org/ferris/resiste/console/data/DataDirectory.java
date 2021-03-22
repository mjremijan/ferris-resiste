package org.ferris.resiste.console.data;

import java.io.File;
import javax.enterprise.inject.Vetoed;
import javax.inject.Inject;
import org.ferris.resiste.console.application.ApplicationDirectory;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class DataDirectory extends File {

    private static final long serialVersionUID = 7491901906021288631L;

    @Inject
    public DataDirectory(ApplicationDirectory appdir) {
        super(appdir, "data");
    }

}
