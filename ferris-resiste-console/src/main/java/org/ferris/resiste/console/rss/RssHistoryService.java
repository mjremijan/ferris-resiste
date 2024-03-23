package org.ferris.resiste.console.rss;


import java.time.Instant;
import java.util.Optional;
import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import static org.ferris.resiste.console.rss.RssHistoryEvent.CLEANUP;
import static org.ferris.resiste.console.rss.RssHistoryEvent.STORE;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class RssHistoryService {

    @Inject
    protected Logger log;

    @Inject
    protected RssHistoryRepository repository;

    public boolean exists(String feedId, String entryId) {
        return repository.find(feedId, entryId).isPresent();
    }


    protected void observeStore(
        @Observes @Priority(STORE) RssHistoryEvent evnt
    ) {
        log.info(String.format("Store feeds in history %s", evnt));
        evnt.getFeeds().stream().forEach(
            f -> f.getEntries().forEach(
                e -> repository.store(
                    new RssHistory(
                          f.getId()
                        , e.getEntryId()
                        , Optional.ofNullable(e.getPublishedDate())
                            .map(d -> d.toInstant())
                            .orElse(Instant.now())
                    )
                )
            )
        );
    }
    
    protected void observeCleanup(
        @Observes @Priority(CLEANUP) RssHistoryEvent evnt
    ) {
        repository.cleanup();        
    }
}
