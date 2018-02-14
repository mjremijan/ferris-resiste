package org.ferris.resiste.console.rome;

import com.rometools.rome.feed.synd.SyndFeed;
import java.util.List;
import javax.annotation.Priority;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.ferris.resiste.console.feed.FeedUrlRepository;
import org.ferris.resiste.console.lang.StringUtils;
import static org.ferris.resiste.console.rome.SyndFilterEvent.FILTER;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class SyndFilterService {

    @Inject
    protected Logger log;

    @Inject
    protected FeedUrlRepository repository;

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
                sf.getEntries().removeIf(se -> {
                    String feedId
                        = StringUtils.firstTrimToNonNull(
                            sf.getLink(), sf.getTitle()
                        ).get();

                    String itemId
                        = se.getUri();

                    return repository.findItemInHistory(
                        feedId, itemId
                    );
                });

                return sf.getEntries().isEmpty();
            }
        );
//        syndFeed.stream().forEach(
//            sf -> sf.getEntries().removeIf(se -> {
//                String feedId
//                    = StringUtils.firstTrimToNonNull(
//                        sf.getLink(), sf.getTitle()
//                    ).get();
//
//                String itemId
//                    = se.getUri();
//
//                return repository.findItemInHistory(
//                    feedId, itemId
//                );
//            })
//        );
    }
}
