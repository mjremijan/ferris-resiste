package org.ferris.resiste.console.io;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.ferris.resiste.console.text.i18n.LocalizedString;
import org.ferris.resiste.console.text.i18n.LocalizedStringList;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class Console {

    private ConsoleWriter writer;

    @Inject
    protected void setConsoleWriter(ConsoleWriter writer) {
        this.writer = writer;
    }


    public void h1(LocalizedString format, Object... args) {
        writer.println();
        writer.printf("[%s]", String.format(format.toString(), args));
        writer.println();
    }

    public void h1(LocalizedString heading) {
        h1(heading, (Object[]) null);
    }

    public void h2(LocalizedString format, Object... args) {
        writer.println();
        writer.printf("++ %s", String.format(format.toString(), args));
        writer.println();
    }

    public void h2(LocalizedString heading) {
        h2(heading, (Object[]) null);
    }

    public void p(LocalizedStringList paragraph) {
        writer.println();
        for (String s : paragraph) {
            writer.printf("%s", s);
            writer.println();
        }
    }

    public void p(LocalizedString format, Object... args) {
        writer.println();
        writer.printf("%s", String.format(format.toString(), args));
        writer.println();
    }

    public void p(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        pw.flush();
        sw.flush();

        writer.println();
        writer.printf(sw.toString());
        writer.println();
    }
}