package org.ferris.resiste.console.rsa;

import java.nio.file.Paths;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.ferris.resiste.console.conf.*;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class Rsa4096Producer {

    protected Rsa4096 rsa;

    @Inject
    public Rsa4096Producer(ConfDirectory confDirectory) throws Exception {
        rsa = new Rsa4096(
            Paths.get(confDirectory.getPath(), "private_key_rsa_4096_pkcs8.pem")
        );
    }

    @Produces
    public Rsa4096 produceRsa4096() {
        return rsa;
    }
}
