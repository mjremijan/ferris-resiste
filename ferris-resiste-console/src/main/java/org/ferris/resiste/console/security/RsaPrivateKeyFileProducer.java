package org.ferris.resiste.console.security;

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
    public RsaPrivateKeyFileProducer(SecurityDirectory securityDirectory) {
        rsaPrivateKey = new RsaPrivateKeyFile(securityDirectory);
    }

    @Produces
    public RsaPrivateKeyFile produceRsaPrivateKeyFile() {
        return rsaPrivateKey;
    }
}
