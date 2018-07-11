package org.ferris.resiste.console.application;

import java.io.File;
import java.net.URI;
import java.net.URL;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class ApplicationDirectoryProducer {

    protected ApplicationDirectory applicationDirectory;

    @PostConstruct
    protected void postConstruct() {
        // This code assumes the following directory structure
        //
        // /app
        //    /bin
        //    /lib
        //      ferris-app-1.0.0.0.jar
        //    /logs
        //
        // So the the application directory will be 1
        // directory up from where the JAR file is located.
        try {
            URL jarURL = ApplicationDirectory.class.getProtectionDomain().getCodeSource().getLocation();
            URI jarURI = jarURL.toURI();
            File jarFile = new File(jarURI);
            File appFile = jarFile.getParentFile().getParentFile();
            applicationDirectory = new ApplicationDirectory(appFile.getAbsolutePath());
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Produces
    protected ApplicationDirectory produceApplicationDirectory() {
        return applicationDirectory;
    }
}
