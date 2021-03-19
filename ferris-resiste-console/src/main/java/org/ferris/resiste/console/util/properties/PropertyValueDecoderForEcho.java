package org.ferris.resiste.console.util.properties;

import java.util.Optional;
import javax.enterprise.inject.Vetoed;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class PropertyValueDecoderForEcho extends PropertyValueDecoder {

    @Override
    public Optional<PropertyValueDecoder> responsible(String value) {
        System.out.printf("PropertyValueDecoderForEcho.responsible {%s}%n", value);
        return Optional.of(this);
    }

    @Override
    public String decode(String value) {
        System.out.printf("PropertyValueDecoderForEcho.decode {%s}%n", value);
        return value;
    }

}
