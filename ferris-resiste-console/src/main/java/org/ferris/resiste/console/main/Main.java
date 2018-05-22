package org.ferris.resiste.console.main;

import java.util.Arrays;
import java.util.List;
import javax.enterprise.event.Event;
import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.inject.Inject;
import org.ferris.resiste.console.email.EmailDraftEvent;
import org.ferris.resiste.console.email.EmailErrorEvent;
import org.ferris.resiste.console.exit.ExitEvent;
import org.ferris.resiste.console.rome.SyndFilterEvent;
import org.ferris.resiste.console.rome.SyndRetrievalEvent;
import org.ferris.resiste.console.rss.RssHistoryEvent;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class Main {

    public static void main(String[] args) {
        SeContainer container
            = SeContainerInitializer.newInstance().initialize();

        Main main
            = container.select(Main.class).get();

        main.main(Arrays.asList(args));
    }

    @Inject
    protected Logger log;

    @Inject
    protected Event<StartupEvent> startupEvent;

    @Inject
    protected Event<EmailDraftEvent> emailSendEvent;

    @Inject
    protected Event<ExitEvent> exitEvent;

    @Inject
    protected Event<SyndRetrievalEvent> retrieval;

    @Inject
    protected Event<SyndFilterEvent> filter;

    @Inject
    protected Event<EmailDraftEvent> send;

    @Inject
    protected Event<RssHistoryEvent> history;

    @Inject
    protected Event<EmailErrorEvent> error;

    protected void main(List<String> args) {
        log.info("Fire StartupEvent");
        startupEvent.fire(new StartupEvent());

        log.info("Fire SyndRetrievalEvent");
        SyndRetrievalEvent retrievalEvent = new SyndRetrievalEvent();
        retrieval.fire(retrievalEvent);

        log.info("Fire SyndFilterEvent");
        SyndFilterEvent filterEvent = new SyndFilterEvent(
            retrievalEvent.getFeeds()
        );
        filter.fire(filterEvent);

        log.info("Fire EmailDraftEvent");
        EmailDraftEvent draftEvent = new EmailDraftEvent(
            filterEvent.getFeeds()
        );
        send.fire(draftEvent);

        log.info("Fire RssHistoryEvent");
        RssHistoryEvent historyEvent = new RssHistoryEvent(
            draftEvent.getFeeds()
        );
        history.fire(historyEvent);

        log.info("Fire EmailErrorEvent");
        EmailErrorEvent errorEvent = new EmailErrorEvent(
            retrievalEvent.getErrors()
        );
        error.fire(errorEvent);


        log.info("Fire ExitEvent");
        exitEvent.fire(new ExitEvent());
    }
}
