package org.ferris.resiste.console.data;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.ferris.resiste.console.application.*;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class DataDirectoryProducer {

    protected DataDirectory dataDirectory;

    @Inject
    protected ApplicationDirectory applicationDirectory;

    @PostConstruct
    public void postConstruct() {
        dataDirectory = new DataDirectory(applicationDirectory);
    }

    @Produces
    public DataDirectory produceConfDirectory() {
        return dataDirectory;
    }
}
