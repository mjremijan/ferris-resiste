package org.ferris.resiste.console.rome;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.util.concurrent.atomic.AtomicInteger;
import org.ferris.resiste.console.io.Console;
import org.ferris.resiste.console.lang.StringUtils;
import static org.ferris.resiste.console.rome.SyndRetrievalEvent.VIEW;
import org.ferris.resiste.console.text.i18n.LocalizedString;
import org.ferris.resiste.console.text.i18n.LocalizedStringKey;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class SyndRetrievalPage {

    @Inject
    protected Logger log;

    @Inject
    protected Console console;

    @Inject
    @LocalizedStringKey("SyndRetrievalPage.Heading")
    protected LocalizedString heading;

    @Inject
    @LocalizedStringKey("SyndRetrievalPage.Title")
    protected LocalizedString title;

    @Inject
    @LocalizedStringKey("SyndRetrievalPage.Entry")
    protected LocalizedString entry;

    protected void observeRetrieval(
        @Observes @Priority(VIEW) SyndRetrievalEvent evnt
    ) {
        log.info(String.format("ENTER %s", evnt));

        console.h1(heading);

        evnt.getFeeds().stream().forEach(
            sf -> {
                console.h2(title, StringUtils.firstTrimToNonNull(sf.getLink(), sf.getTitle()).get());
                AtomicInteger ai = new AtomicInteger(1);
                sf.getEntries().stream().forEach(
                    se -> console.p(entry, ai.getAndIncrement(), se.getEntryId())
                );
            }
        );

    }
}
