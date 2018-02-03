package org.ferris.resiste.console.main;

import java.util.Arrays;
import java.util.List;
import javax.enterprise.event.Event;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.ferris.resiste.console.email.EmailSendEvent;
import org.ferris.resiste.console.exit.ExitEvent;
import org.ferris.resiste.console.twitter.TweetRetrievalEvent;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class Main {
    public static void main(String[] args) {
        CDI<Object> cdi = CDI.getCDIProvider().initialize();        
        Main main
            = cdi.select(Main.class).get();
        main.main(Arrays.asList(args));
    }
    
    @Inject
    protected Logger log;

    @Inject
    protected Event<StartupEvent> startupEvent;

    @Inject
    protected Event<TweetRetrievalEvent> tweetRetrievalEvent;

    @Inject
    protected Event<EmailSendEvent> emailSendEvent;

    @Inject
    protected Event<ExitEvent> exitEvent;
    
    protected void main(List<String> args) {
        log.info("Fire StartupEvent");
        startupEvent.fire(new StartupEvent());

        log.info("Fire TweetRetrievalEvent");
        TweetRetrievalEvent trEvent = new TweetRetrievalEvent();
        tweetRetrievalEvent.fire(trEvent);

        log.info("Fire EmailSendEvent");
        EmailSendEvent esEvnt = new EmailSendEvent(trEvent.getNewTweetsFromThisRun());
        emailSendEvent.fire(esEvnt);

        log.info("Fire ExitEvent");
        exitEvent.fire(new ExitEvent());
    }
}
