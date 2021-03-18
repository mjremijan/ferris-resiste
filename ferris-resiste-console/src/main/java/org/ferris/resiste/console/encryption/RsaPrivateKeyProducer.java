package org.ferris.resiste.console.encryption;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class RsaPrivateKeyProducer {

    protected RsaPrivateKey rsaPrivateKey;

    @Inject
    public RsaPrivateKeyProducer(EncryptionDirectory encryptionDirectory) {
        rsaPrivateKey = new RsaPrivateKey(encryptionDirectory);
    }

    @Produces
    public RsaPrivateKey produceRsaPrivateKey() {
        return rsaPrivateKey;
    }
}
