package org.ferris.resiste.console.encryption;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class RsaPrivateKeyFileProducer {

    protected RsaPrivateKeyFile rsaPrivateKey;

    @Inject
    public RsaPrivateKeyFileProducer(EncryptionDirectory encryptionDirectory) {
        rsaPrivateKey = new RsaPrivateKeyFile(encryptionDirectory);
    }

    @Produces
    public RsaPrivateKeyFile produceRsaPrivateKey() {
        return rsaPrivateKey;
    }
}
