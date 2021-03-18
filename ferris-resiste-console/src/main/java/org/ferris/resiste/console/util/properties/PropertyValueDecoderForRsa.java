package org.ferris.resiste.console.util.properties;

import java.util.Optional;
import org.ferris.resiste.console.encryption.Rsa;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class PropertyValueDecoderForRsa extends PropertyValueDecoder {

    protected Rsa rsa;

    protected PropertyValueDecoderForRsa(Rsa rsa) {
        this.rsa = rsa;
    }

    @Override
    public Optional<PropertyValueDecoder> responsible(String value) {
        return
            (value == null)
                ? super.responsible(value)
                : (
                  (value.startsWith("rsa{") && value.charAt(value.length()-1) == '}')
                    ? Optional.of(this)
                    : super.responsible(value)
                )
            ;
    }

    @Override
    public String decode(String value) {
        String base64 = value.substring(0, value.length()-1).substring(4);
        return rsa.getValue(base64);
    }

}
