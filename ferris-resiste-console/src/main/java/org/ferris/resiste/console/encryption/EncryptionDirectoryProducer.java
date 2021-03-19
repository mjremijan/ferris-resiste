package org.ferris.resiste.console.encryption;

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
    public void EncryptionDirectoryProducer(ApplicationDirectory applicationDirectory) {
        encryptionDirectory = new EncryptionDirectory(applicationDirectory);
    }

    @Produces
    public EncryptionDirectory produceEncryptionDirectory() {
        return encryptionDirectory;
    }
}
