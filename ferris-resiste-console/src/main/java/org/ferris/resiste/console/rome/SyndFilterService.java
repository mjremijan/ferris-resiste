package org.ferris.resiste.console.rome;

import java.util.List;
import javax.annotation.Priority;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import static org.ferris.resiste.console.rome.SyndFilterEvent.FILTER;
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
    protected RssHistoryService service;

    protected void observeFilter(
        @Observes @Priority(FILTER) SyndFilterEvent evnt
    ) {
        log.info(String.format("ENTER %s", evnt));

        log.info("READ: Get SyndFeed list from event");
        List<SyndFeed> syndFeed
            = evnt.getFeeds();

        log.info("PROCESS: Remove entries that exist in history");
        syndFeed.removeIf(
            sf -> {
                sf.getEntries().removeIf(se ->
                    service.exists(sf.getId(), se.getGuid())
                );
                return sf.getEntries().isEmpty();
            }
        );
    }
}
