package org.ferris.resiste.console.encryption;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class Rsa4096Producer {

    protected Rsa rsa;

    @Inject
    public Rsa4096Producer(RsaPrivateKey rsaPrivateKey) throws Exception {

        if (rsaPrivateKey.canRead()) {
            rsa = new RsaDecrypt(rsaPrivateKey);
        } else {
            rsa = new Rsa4096Echo();
        }
    }

    @Produces
    public Rsa produceRsa4096() {
        return rsa;
    }
}
