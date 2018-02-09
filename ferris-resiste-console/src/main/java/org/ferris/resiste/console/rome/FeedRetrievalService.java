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
import org.apache.log4j.Logger;
import org.ferris.resiste.console.feed.Feed;
import org.ferris.resiste.console.feed.FeedRepository;
import static org.ferris.resiste.console.rome.FeedRetrievalEvent.RETRIEVE;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class FeedRetrievalService {

    @Inject
    protected Logger log;

    @Inject
    protected FeedRepository repository;

    protected void retrieveObserver(
        @Observes @Priority(RETRIEVE) FeedRetrievalEvent evnt
    ) {
        log.info("ENTER");

        log.info("READ: Get the list of all the user's feed URLs");
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
                    , Arrays.toString(e.getStackTrace())
                );
            }
        }

        // WRITE
        log.info("WRITE: Store feed data for next step");
        evnt.setFeeds(feeds);
    }
}
