package org.ferris.resiste.console.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import java.util.Optional;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class RsaProducer {

    protected Optional<Rsa> rsa;

    @Inject
    public RsaProducer(RsaPrivateKeyFile rsaPrivateKey) throws Exception {
        if (rsaPrivateKey.canRead()) {
            rsa = Optional.of(new Rsa(rsaPrivateKey));
        } else {
            rsa = Optional.empty();
        }
    }

    @Produces
    public Optional<Rsa> produceRsa() {
        return rsa;
    }
}
