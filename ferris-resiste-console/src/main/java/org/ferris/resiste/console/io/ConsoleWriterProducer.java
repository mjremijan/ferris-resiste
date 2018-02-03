package org.ferris.resiste.console.io;

import java.io.PrintWriter;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class ConsoleWriterProducer {

    @Produces
    public ConsoleWriter produceConsoleWriter() {
        return new ConsoleWriter(new PrintWriter(System.out));
    }
}
