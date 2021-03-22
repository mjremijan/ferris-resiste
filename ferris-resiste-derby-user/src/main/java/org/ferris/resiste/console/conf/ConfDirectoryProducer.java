package org.ferris.resiste.console.conf;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.ferris.resiste.console.application.*;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class ConfDirectoryProducer {

    protected ConfDirectory confDirectory;

    @Inject
    protected ApplicationDirectory applicationDirectory;

    @PostConstruct
    public void postConstruct() {
        confDirectory = new ConfDirectory(applicationDirectory);
    }

    @Produces
    public ConfDirectory produceConfDirectory() {
        return confDirectory;
    }
}
