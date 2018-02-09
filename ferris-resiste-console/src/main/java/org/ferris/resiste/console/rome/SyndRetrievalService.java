package org.ferris.resiste.console.rome;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Priority;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.ferris.resiste.console.feed.Feed;
import org.ferris.resiste.console.feed.FeedRepository;
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
    protected FeedRepository repository;

    protected void observeRetrieve(
        @Observes @Priority(RETRIEVE) SyndRetrievalEvent evnt
    ) {
        log.info(String.format("ENTER %s", evnt));

        log.info("READ: Get the list of all the user's URLs");
        List<Feed> userFeeds
            = repository.findAll();

        log.info("PROCESS: Convert the URLs to feed data");
        List<SyndFeed> feeds = new ArrayList<>(userFeeds.size());
        for (Feed f : userFeeds) {
            try {
                feeds.add(
                    new SyndFeedInput().build(new XmlReader(f.getUrl()))
                );
            } catch (Exception e) {
                evnt.addError(
                    f.getUrl().toString()
                    + ": "
                    + Arrays.toString(e.getStackTrace())
                );
            }
        }

        // WRITE
        log.info("WRITE: Store feed data for next step");
        evnt.setFeeds(feeds);
    }
}
