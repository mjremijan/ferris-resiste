package org.ferris.resiste.console.rome;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.ferris.resiste.console.rome.SyndRetrievalEvent.RETRIEVE;
import org.ferris.resiste.console.rss.RssFeed;
import org.ferris.resiste.console.rss.RssFeedFactory;
import org.ferris.resiste.console.rss.RssUrl;
import org.ferris.resiste.console.rss.RssUrlService;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class SyndRetrievalService {

    @Inject
    protected Logger log;

    @Inject
    protected RssUrlService service;

    @Inject
    protected RssFeedFactory factory;


    protected void observeRetrieve(
        @Observes @Priority(RETRIEVE) SyndRetrievalEvent evnt
    ) {
        log.info(String.format("Retrieve all of the user's feeds %s", evnt));

        List<RssUrl> userFeeds
            = service.findAll();

        List<RssFeed> feeds = new ArrayList<>(userFeeds.size());
        userFeeds.forEach(f -> {
            try {
                feeds.add(
                    factory.build(f)
                );
            } catch (Exception e) {
                evnt.addError(
                    String.format("%s: %s - %s", f.getUrl().toString(), e.getMessage(), Arrays.toString(e.getStackTrace()))
                );
            }
        });

        evnt.setFeeds(feeds);
    }
}
