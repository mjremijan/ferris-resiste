package org.ferris.resiste.console.exception;

import javax.inject.Inject;
import org.ferris.resiste.console.io.Console;
import org.ferris.resiste.console.text.i18n.LocalizedString;
import org.ferris.resiste.console.text.i18n.LocalizedStringKey;
import org.ferris.resiste.console.view.page.AbstractPage;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class UncaughtExceptionPage extends AbstractPage {

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
