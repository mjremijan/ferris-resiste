package org.ferris.resiste.console.io;

import java.io.PrintWriter;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

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
