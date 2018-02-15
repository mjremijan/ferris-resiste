package org.ferris.resiste.console.rss;


import java.time.Instant;
import java.util.Optional;
import javax.annotation.Priority;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import static org.ferris.resiste.console.rss.RssHistoryEvent.STORE;
import static org.ferris.resiste.console.rss.RssHistoryEvent.VACUUM;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class RssHistoryService {

    @Inject
    protected Logger log;

    @Inject
    protected RssHistoryRepository repository;

    public boolean exists(String feedId, String entryId) {
        log.info(String.format("ENTER \"%s\", \"%s\"", feedId, entryId));
        return repository.find(feedId, entryId).isPresent();
    }


    protected void observeStore(
        @Observes @Priority(STORE) RssHistoryEvent evnt
    ) {
        log.info(String.format("ENTER %s", evnt));
        evnt.getFeeds().stream().forEach(
            f -> f.getEntries().forEach(
                e -> repository.store(
                    new RssHistory(
                          f.getId()
                        , e.getGuid()
                        , Optional.ofNullable(e.getPublishedDate())
                            .map(d -> d.toInstant())
                            .orElse(Instant.now())
                    )
                )
            )
        );
    }


    protected void observeVacuum(
        @Observes @Priority(VACUUM) RssHistoryEvent evnt
    ) {
        log.info(String.format("ENTER %s", evnt));
        evnt.getFeeds().stream().forEach(
            f -> repository.removeOlderThan(
                f.getId(), f.getOldestPublishedDate()
            )
        );
    }
}
