package org.ferris.resiste.console.rsa;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.enterprise.inject.Vetoed;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class Rsa4096 {

    private KeyFactory keyFactory;
    private PrivateKey privateKey;

    public Rsa4096(
        Path fullyQualifiedPrivateKeyFileName
    ) throws Exception {
        setKeyFactory();
        setPrivateKey(fullyQualifiedPrivateKeyFileName);
    }

    protected void setKeyFactory() throws Exception {
        this.keyFactory = KeyFactory.getInstance("RSA");
    }

    protected void setPrivateKey(Path fullyQualifiedPrivateKeyFileName)
        throws Exception {
        String stringBefore
            = Files.readAllLines(fullyQualifiedPrivateKeyFileName)
                .stream()
                .reduce((t, u) -> t + "\n" + u)
                .get()
        ;

        String stringAfter = stringBefore
            .replaceAll("\\n", "")
            .replaceAll("-----BEGIN PRIVATE KEY-----", "")
            .replaceAll("-----END PRIVATE KEY-----", "")
            .trim();

        byte[] decoded = Base64
            .getDecoder()
            .decode(stringAfter);

        KeySpec keySpec
            = new PKCS8EncodedKeySpec(decoded);

        privateKey = keyFactory.generatePrivate(keySpec);
    }

    public String decryptFromBase64(String base64EncodedEncryptedBytes) {
        String plainText = null;
        try {
            final Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decoded = Base64
                .getDecoder()
                .decode(base64EncodedEncryptedBytes);
            byte[] decrypted = cipher.doFinal(decoded);
            plainText = new String(decrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return plainText;
    }


}
