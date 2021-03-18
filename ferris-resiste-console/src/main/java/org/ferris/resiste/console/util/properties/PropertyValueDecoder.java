package org.ferris.resiste.console.util.properties;

import java.util.Optional;
import javax.enterprise.inject.Vetoed;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public abstract class PropertyValueDecoder {

    protected abstract String decode(String value);

    protected Optional<PropertyValueDecoder> next = Optional.empty();

    public void next(PropertyValueDecoder next) {
        if (next == null) {
            throw new IllegalArgumentException("PropertyValueDecoder \"next\" parameter cannot be null");
        }
        this.next = Optional.of(next);
    }

    protected Optional<PropertyValueDecoder> responsible(String value) {
        return next.
             map(pvd -> pvd.responsible(value))
            .orElseGet(() -> Optional.empty());
    }
}
