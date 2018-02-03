package org.ferris.resiste.console.email;

import java.io.File;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.ferris.resiste.console.application.ApplicationDirectory;
import org.ferris.resiste.console.io.AbstractPropertiesFile;

/**
 * This is a hard coded {@link File} object to "conf/email.properties"
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class EmailPropertiesFile extends AbstractPropertiesFile {

    private static final long serialVersionUID = 140988098234509827L;

    /**
     * To file "conf/email.properties"
     *
     * @param appdir An {@link ApplicationDirectory} representing the root
     * directory Tweials running in.
     */
    @Inject
    public EmailPropertiesFile(ApplicationDirectory appdir) {
        super(appdir, String.format("conf/email.properties"));
    }
}
