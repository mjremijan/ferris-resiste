package org.ferris.resiste.console.encryption;

import org.ferris.resiste.console.conf.*;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.ferris.resiste.console.application.*;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class EncryptionDirectoryProducer {

    protected EncryptionDirectory encryptionDirectory;

    @Inject
    protected ApplicationDirectory applicationDirectory;

    @PostConstruct
    public void postConstruct() {
        encryptionDirectory = new EncryptionDirectory(applicationDirectory);
    }

    @Produces
    public EncryptionDirectory produceConfDirectory() {
        return encryptionDirectory;
    }
}
