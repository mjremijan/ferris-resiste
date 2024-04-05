package org.ferris.resiste.console.email;

import jakarta.enterprise.inject.Vetoed;
import jakarta.inject.Inject;
import java.io.File;
import org.ferris.resiste.console.conf.ConfDirectory;
import org.ferris.resiste.console.io.AbstractPropertiesFile;

/**
 * This is a hard coded {@link File} object to "conf/email.properties"
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class EmailAccountPropertiesFile extends AbstractPropertiesFile {

    private static final long serialVersionUID = 140988098234509827L;

    /**
     * To file "conf/email.properties"
     *
     * @param confdir A {@link ConfDirectory}
     */
    @Inject
    public EmailAccountPropertiesFile(ConfDirectory confdir) {
        super(confdir, String.format("email.properties"));
    }
}
