package org.ferris.resiste.console.encryption;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

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
