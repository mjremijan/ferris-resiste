package org.ferris.resiste.console.encryption;

import java.io.File;
import javax.enterprise.inject.Vetoed;

/**
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class RsaPrivateKey extends File {

    private static final long serialVersionUID = 182745237455451276L;

    /**
     * Pass to super constructor w/o error checking
     *
     * @param parent The parent, relative to the {@code relativePath} parameter
     */
    public RsaPrivateKey(File parent) {
        super(parent, "private_key_rsa_4096_pkcs8.pem");
    }
}
