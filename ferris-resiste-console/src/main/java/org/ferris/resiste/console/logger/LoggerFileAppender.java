package org.ferris.resiste.console.logger;

import ch.qos.logback.core.rolling.RollingFileAppender;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Extends {@link RollingFileAppender} to programmatically
 * find the application's "logs" directory.
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class LoggerFileAppender extends ch.qos.logback.core.rolling.RollingFileAppender {
    public LoggerFileAppender() throws URISyntaxException {
        super();
        // This code assumes the following directory structure
        //
        // /project
        //    /bin
        //    /lib
        //      ferris-project-app-1.0.0.0-SNAPSHOT.jar
        //    /logs
        //
        // So the the log directory will be 1 directory up from where the
        // JAR file is located.
        URL jarURL = this.getClass().getProtectionDomain().getCodeSource().getLocation();
        URI jarURI = jarURL.toURI();
        File jarFile = new File(jarURI);
        File parentDir = jarFile.getParentFile().getParentFile();
        File logsDir = new File(parentDir, "logs");
        File logFile = new File(logsDir, "application.log");
        System.out.printf("Setting file %s%n", logFile.toString());
        super.setFile(logFile.toString());
    }
}
