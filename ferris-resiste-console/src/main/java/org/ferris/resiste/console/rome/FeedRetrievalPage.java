package org.ferris.resiste.console.rome;

import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Priority;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.ferris.resiste.console.io.Console;
import static org.ferris.resiste.console.rome.FeedRetrievalEvent.VIEW;
import org.ferris.resiste.console.text.i18n.LocalizedString;
import org.ferris.resiste.console.text.i18n.LocalizedStringKey;
import org.ferris.resiste.console.view.page.AbstractPage;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class FeedRetrievalPage extends AbstractPage {

    @Inject
    protected Logger log;

    @Inject
    protected Console console;

    @Inject
    @LocalizedStringKey("FeedRetrievalPage.Heading")
    protected LocalizedString heading;

    @Inject
    @LocalizedStringKey("FeedRetrievalPage.Title")
    protected LocalizedString title;

    @Inject
    @LocalizedStringKey("FeedRetrievalPage.Entry")
    protected LocalizedString entry;

    protected void retrieveObserver(
        @Observes @Priority(VIEW) FeedRetrievalEvent evnt
    ) {
        console.h1(heading);

        evnt.getFeeds().stream().forEach(
            sf -> {
                console.h2(title, sf.getTitle());
                AtomicInteger ai = new AtomicInteger(1);
                sf.getEntries().stream().forEach(
                    se -> console.p(entry, ai.getAndIncrement(), se.getLink())
                );
            }
        );

    }
}
