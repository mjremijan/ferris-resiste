package org.ferris.resiste.console.io;

import java.io.PrintWriter;
import javax.enterprise.inject.Vetoed;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class ConsoleWriter {

    private PrintWriter writer;

    public ConsoleWriter(PrintWriter writer) {
        this.writer = writer;
    }

    public void printf(String format, Object... args) {
        writer.printf(format, args);
        writer.flush();
    }

    public void print(String str) {
        writer.print(str);
        writer.flush();
    }

    public void println() {
        writer.println();
        writer.flush();
    }
}
