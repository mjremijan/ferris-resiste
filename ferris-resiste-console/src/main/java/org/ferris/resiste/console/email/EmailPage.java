package org.ferris.resiste.console.email;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.ferris.resiste.console.io.Console;
import org.ferris.resiste.console.text.i18n.LocalizedString;
import org.ferris.resiste.console.text.i18n.LocalizedStringKey;
import org.ferris.resiste.console.view.page.AbstractPage;
import org.jboss.weld.experimental.Priority;

/**
 * This is the console view for email related information.
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailPage extends AbstractPage {

    @Inject
    protected Console console;

    @Inject
    protected Logger log;

    @Inject
    @LocalizedStringKey("EmailPage.Heading")
    protected LocalizedString heading;

    public void render(
        @Observes @Priority(EmailSendPriority.PRINT_EMAIL_MESSAGE)
        EmailSendEvent evnt
    ) {
        log.info("Email message:");
        console.h1(heading);
        console.p(new LocalizedString(
            evnt.getMessage()
        ));
    }
}
