package org.ferris.resiste.console.email;

import java.io.File;
import javax.inject.Inject;
import org.ferris.resiste.console.conf.ConfDirectory;
import org.ferris.resiste.console.io.AbstractPropertiesFile;

/**
 * This is a hard coded {@link File} object to "conf/email.properties"
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailPropertiesFile extends AbstractPropertiesFile {

    private static final long serialVersionUID = 140988098234509827L;

    /**
     * To file "conf/email.properties"
     *
     * @param confdir A {@link ConfDirectory}
     */
    @Inject
    public EmailPropertiesFile(ConfDirectory confdir) {
        super(confdir, String.format("conf/email.properties"));
    }
}
