package org.ferris.resiste.console.conf;

import java.io.File;
import javax.inject.Inject;
import org.ferris.resiste.console.application.ApplicationDirectory;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class ConfDirectory extends File {

    private static final long serialVersionUID = 7491901906021288631L;

    @Inject
    public ConfDirectory(ApplicationDirectory appdir) {
        super(appdir, "conf");
    }

}
