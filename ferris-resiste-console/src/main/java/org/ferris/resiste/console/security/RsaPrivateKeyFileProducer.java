package org.ferris.resiste.console.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class RsaPrivateKeyFileProducer {

    protected RsaPrivateKeyFile rsaPrivateKey;

    @Inject
    public RsaPrivateKeyFileProducer(SecurityDirectory securityDirectory) {
        rsaPrivateKey = new RsaPrivateKeyFile(securityDirectory);
    }

    @Produces
    public RsaPrivateKeyFile produceRsaPrivateKeyFile() {
        return rsaPrivateKey;
    }
}
