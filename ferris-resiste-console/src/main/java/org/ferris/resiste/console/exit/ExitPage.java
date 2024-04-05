package org.ferris.resiste.console.exit;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.ferris.resiste.console.io.Console;
import org.ferris.resiste.console.text.i18n.LocalizedString;
import org.ferris.resiste.console.text.i18n.LocalizedStringKey;
import org.slf4j.Logger;

@ApplicationScoped
public class ExitPage {

    @Inject
    protected Console console;

    @Inject
    protected Logger log;

    @Inject
    @LocalizedStringKey("ExitPage.Heading")
    protected LocalizedString heading;

    @Inject
    @LocalizedStringKey("ExitPage.Goodbye")
    protected LocalizedString goodbye;

    public void view() {
        log.info("Application exiting");
        console.h1(heading);
        console.p(goodbye);
    }

}
