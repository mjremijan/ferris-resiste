package org.ferris.resiste.console.rome;

import java.util.List;
import javax.annotation.Priority;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import static org.ferris.resiste.console.rome.SyndFilterEvent.FILTER;
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

        // Loop over all the feeds, remove feed if it has no more entires
        rssFeeds.removeIf(
            rf -> {
                // Loop over all the entries in a feed, remove entry if it exists in history
                rf.getEntries().removeIf(re -> {
                    String feedId = rf.getId();
                    String entryId = re.getGuid();
                    boolean exists = historyService.exists(feedId, entryId);
                    if (!exists) {
                        log.info(String.format(
                              "History miss! This is a new entry: feed=\"%s\", entry=\"%s\", published=%s."
                            , feedId, entryId, re.getPublishedDate().toInstant().toString()
                        ));
                    }
                    return exists;
                });
                return rf.getEntries().isEmpty();
            }
        );

    }
}
