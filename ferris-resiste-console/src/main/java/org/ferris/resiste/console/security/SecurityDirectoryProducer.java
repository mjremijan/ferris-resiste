package org.ferris.resiste.console.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.ferris.resiste.console.application.*;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class SecurityDirectoryProducer {

    protected SecurityDirectory securityDirectory;

    @Inject
    public SecurityDirectoryProducer(ApplicationDirectory applicationDirectory) {
        securityDirectory = new SecurityDirectory(applicationDirectory);
    }

    @Produces
    public SecurityDirectory produceSecurityDirectory() {
        return securityDirectory;
    }
}
