package org.ferris.resiste.console.main;

import java.util.Arrays;
import java.util.List;
import javax.enterprise.event.Event;
import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.inject.Inject;
import org.ferris.resiste.console.email.EmailSendEvent;
import org.ferris.resiste.console.exit.ExitEvent;
import org.ferris.resiste.console.rome.SyndFilterEvent;
import org.ferris.resiste.console.rome.SyndRetrievalEvent;
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
    protected Event<EmailSendEvent> emailSendEvent;

    @Inject
    protected Event<ExitEvent> exitEvent;

    @Inject
    protected Event<SyndRetrievalEvent> retrieval;

    @Inject
    protected Event<SyndFilterEvent> filter;

    protected void main(List<String> args) {
        log.info("Fire StartupEvent");
        startupEvent.fire(new StartupEvent());

        log.info("Fire SyndRetrievalEvent");
        SyndRetrievalEvent retrievalEvent = new SyndRetrievalEvent();
        retrieval.fire(retrievalEvent);

        log.info("Fire SyndFilterEvent");
        SyndFilterEvent filterEvent = new SyndFilterEvent(
            retrievalEvent.getFeeds(), retrievalEvent.getErrors()
        );
        filter.fire(filterEvent);


        log.info("Fire ExitEvent");
        exitEvent.fire(new ExitEvent());
    }
}
