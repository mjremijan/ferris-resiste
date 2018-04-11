package org.ferris.resiste.console.email;

import javax.annotation.Priority;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import static org.ferris.resiste.console.email.EmailDraftEvent.DRAFT_VIEW;
import org.ferris.resiste.console.io.Console;
import org.ferris.resiste.console.text.i18n.LocalizedString;
import org.ferris.resiste.console.text.i18n.LocalizedStringKey;
import org.ferris.resiste.console.view.page.AbstractPage;
import org.slf4j.Logger;


/**
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailDraftPage extends AbstractPage {

    @Inject
    protected Console console;

    @Inject
    protected Logger log;

    @Inject
    @LocalizedStringKey("EmailDraftPage.Heading")
    protected LocalizedString heading;

    @Inject
    @LocalizedStringKey("EmailDraftPage.Draft")
    protected LocalizedString draft;

    public void observeSend(
        @Observes @Priority(DRAFT_VIEW) EmailDraftEvent evnt
    ) {
        console.h1(heading);
        evnt.getDrafts().stream().forEach(
            d -> console.p(LocalizedString.format(
                  draft.toString()
                , d.getSubject()
                , d.getBody()
            ))
        );
    }
}
