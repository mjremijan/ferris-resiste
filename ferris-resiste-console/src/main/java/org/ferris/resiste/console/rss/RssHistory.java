package org.ferris.resiste.console.rss;

import jakarta.enterprise.inject.Vetoed;
import java.time.Instant;
import java.util.Optional;
import java.util.StringJoiner;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class RssHistory {
    protected String feedId;
    protected String entryId;
    protected Instant published;

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[RssHistory ", "]");
        sj.add(String.format("feedId:%s", Optional.ofNullable(feedId).orElse("null")));
        sj.add(String.format("entryId:%s", Optional.ofNullable(entryId).orElse("null")));
        sj.add(String.format("published:%s", Optional.ofNullable(published).map(p->p.toString()).orElse("null")));
        return sj.toString();
    }

    public String getFeedId() {
        return feedId;
    }

    public String getEntryId() {
        return entryId;
    }

    public Instant getPublished() {
        return published;
    }

    public RssHistory(String feedId, String itemId, Instant published) {
        this.feedId = feedId;
        this.entryId = itemId;
        this.published = published;
    }
}
