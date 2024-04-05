package org.ferris.resiste.console.io;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import java.io.PrintWriter;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class ConsoleWriterProducer {

    protected ConsoleWriter consoleWriter;

    @PostConstruct
    protected void postConstruct() {
        consoleWriter = new ConsoleWriter(new PrintWriter(System.out));
    }

    @Produces
    protected ConsoleWriter produceConsoleWriter() {
        return consoleWriter;
    }
}
