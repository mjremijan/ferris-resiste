package org.ferris.resiste.console.security;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
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
