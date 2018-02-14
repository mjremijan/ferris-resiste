package org.ferris.resiste.console.rome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Priority;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.ferris.resiste.console.feed.FeedUrl;
import org.ferris.resiste.console.feed.FeedUrlRepository;
import static org.ferris.resiste.console.rome.SyndRetrievalEvent.RETRIEVE;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class SyndRetrievalService {

    @Inject
    protected Logger log;

    @Inject
    protected FeedUrlRepository repository;

    @Inject
    protected SyndFeedFactory factory;

    protected void observeRetrieve(
        @Observes @Priority(RETRIEVE) SyndRetrievalEvent evnt
    ) {
        log.info(String.format("ENTER %s", evnt));

        log.info("READ: Get the list of all the user's URLs");
        List<FeedUrl> userFeeds
            = repository.findAll();

        log.info("PROCESS: Convert the URLs to feed data");
        List<SyndFeed> feeds = new ArrayList<>(userFeeds.size());
        userFeeds.forEach(f -> {
            try {
                feeds.add(
                    factory.build(f)
                );
            } catch (Exception e) {
                evnt.addError(
                    f.getUrl().toString()
                    + ": "
                    + Arrays.toString(e.getStackTrace())
                );
            }
        });

        // WRITE
        log.info("WRITE: Store feed data for next step");
        evnt.setFeeds(feeds);
    }
}
