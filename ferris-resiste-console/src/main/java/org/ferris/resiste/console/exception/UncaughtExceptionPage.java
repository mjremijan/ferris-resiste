package org.ferris.resiste.console.exception;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.ferris.resiste.console.io.Console;
import org.ferris.resiste.console.text.i18n.LocalizedString;
import org.ferris.resiste.console.text.i18n.LocalizedStringKey;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class UncaughtExceptionPage {

    @Inject
    protected Logger log;

    @Inject
    protected Console console;

    @Inject
    @LocalizedStringKey("UncaughtExceptionPage.Heading")
    protected LocalizedString heading;

    @Inject
    @LocalizedStringKey("UncaughtExceptionPage.Opps")
    protected LocalizedString opps;

    public void view(Throwable e) {
        log.error(e.getMessage(), e);

        console.h1(heading);
        console.p(opps);
        console.p(e);
    }
}
