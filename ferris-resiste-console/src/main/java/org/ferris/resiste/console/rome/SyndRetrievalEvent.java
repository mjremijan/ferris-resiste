package org.ferris.resiste.console.rome;

import org.ferris.resiste.console.rss.RssFeed;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class SyndRetrievalEvent {

    public static final int RETRIEVE = 100;

    public static final int VIEW = 200;

    protected List<String> errors;

    protected List<RssFeed> feeds;

    public SyndRetrievalEvent() {
        errors = new LinkedList<>();
    }

    public void setFeeds(List<RssFeed> feeds) {
        this.feeds = feeds;
    }

    public List<RssFeed> getFeeds() {
        return feeds;
    }

    public void addError(String message) {
        errors.add(message);
    }

    public List<String> getErrors() {
        return errors;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[SyndRetrievalEvent ", "]");
        sj.add(String.format("feeds:%s", (feeds == null) ? "null" : feeds.size()));
        sj.add(String.format("errors:%s", (errors == null) ? "null" : errors.size()));
        return sj.toString();
    }
}
