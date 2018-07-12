package org.ferris.resiste.console.logger;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.enterprise.inject.Vetoed;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class TimeBasedRollingPolicy extends ch.qos.logback.core.rolling.TimeBasedRollingPolicy
{
    public TimeBasedRollingPolicy() throws URISyntaxException {
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
        String rollingNamePattern = logsDir + File.separator + "application.%d{yyyy-MM}.log";
        System.out.printf("rollingNamePattern=%s%n", rollingNamePattern);
        super.setFileNamePattern(rollingNamePattern);
        // https://stackoverflow.com/questions/10953915/filenamepattern-in-rollingfileappender-logback-configuration
    }
}
