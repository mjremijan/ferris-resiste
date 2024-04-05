package org.ferris.resiste.console.rome;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.util.List;
import static org.ferris.resiste.console.rome.SyndFilterEvent.FILTER_BY_HISTORY;
import static org.ferris.resiste.console.rome.SyndFilterEvent.FILTER_BY_REGEX;
import org.ferris.resiste.console.rss.RssFeed;
import org.ferris.resiste.console.rss.RssHistoryService;
import org.ferris.resiste.console.rss.RssUrlService;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class SyndFilterService {

    @Inject
    protected Logger log;

    @Inject
    protected RssHistoryService historyService;
    
    @Inject
    protected RssUrlService service;
    
    protected void observeFilterByRegEx(
        @Observes @Priority(FILTER_BY_REGEX) SyndFilterEvent evnt
    ) {
        log.info(String.format("Filter out RssFeed entries that don't match the RegEx the user defined for the RSS Url %s", evnt));
        
        // Loop over all RssUrl objects
        service.findAll().stream()
            // Find RssUrl  that have a pattern
            .filter(u -> u.getPattern().isPresent())
            // Loop over RssUrl that have a pattern 
            .forEach(u -> {
                evnt.getFeeds().stream()
                    // Find RssFeed id matching RssUrl id
                    .filter(f -> f.getId().equals(u.getId()))
                    // Loop over all RssFeed.entries 
                    .forEach(f -> f.getEntries()
                        // Remove if pattern does not match title and does not match contents
                        .removeIf(
                            e -> { 
                                boolean foundInTitle = u.getPattern().get().matcher(e.getTitle()).find();
                                boolean foundInContent = u.getPattern().get().matcher(e.getContents()).find();
                                boolean b = ( foundInTitle == false &&  foundInContent == false);
                                if (b) {
                                    log.info(String.format(
                                        "Entry does not match regex \"%s\" [Entry feedId=\"%s\", entryId=\"%s\", title=\"%s\", contents=\"%s\"]%n"
                                        , u.getPattern().get().pattern()
                                        , e.getFeedId()
                                        , e.getEntryId()
                                        , e.getTitle()
                                        , e.getContents()
                                    ));
                                }
                                return b;
                            }
                        )
                    )
                ;
            });
        ;
    }

    protected void observeFilterByHistory(
        @Observes @Priority(FILTER_BY_HISTORY) SyndFilterEvent evnt
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
                    String entryId = re.getEntryId();
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
