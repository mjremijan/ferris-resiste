package org.ferris.resiste.console.rome;

import java.util.Iterator;
import java.util.List;
import javax.annotation.Priority;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import static org.ferris.resiste.console.rome.SyndFilterEvent.FILTER;
import org.ferris.resiste.console.rss.RssEntry;
import org.ferris.resiste.console.rss.RssFeed;
import org.ferris.resiste.console.rss.RssHistoryService;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class SyndFilterService {

    @Inject
    protected Logger log;

    @Inject
    protected RssHistoryService historyService;

    protected void observeFilter(
        @Observes @Priority(FILTER) SyndFilterEvent evnt
    ) {
        log.info(String.format("Filter out RssFeed entries already in history %s", evnt));

        List<RssFeed> rssFeeds
            = evnt.getFeeds();

        // Loop over all feeds
        for (Iterator<RssFeed> feedsItr = rssFeeds.iterator(); feedsItr.hasNext();) {
            RssFeed rssFeed = feedsItr.next();
            String feedId = rssFeed.getId();

            // Loop over all entries in a feed
            for (Iterator<RssEntry> entriesItr = rssFeed.getEntries().iterator(); entriesItr.hasNext();) {
                RssEntry rssEntry = entriesItr.next();
                String entryId = rssEntry.getGuid();
                boolean exists = historyService.exists(feedId, entryId);
                if (exists) {
                    entriesItr.remove();
                } else {
                    log.info(String.format("History miss! This is new: feed=\"%s\", entry=\"%s\"", feedId, entryId));
                }
            }

            // If a feed no longer had any entries, remove the feed
            if (rssFeed.getEntries().isEmpty()) {
                log.info(String.format("No more entries so remove feed=\"%s\"", feedId));
                feedsItr.remove();
            }
        }
    }
}
