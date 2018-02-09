package org.ferris.resiste.console.rome;

import com.rometools.rome.feed.synd.SyndFeed;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class SyndFilterEvent {

    public static final int FILTER = 100;

    public static final int VIEW = 200;

    protected List<String> errors;

    protected List<SyndFeed> feeds;

    public SyndFilterEvent(List<SyndFeed> feeds, List<String> errors) {
        this.feeds = new ArrayList<>(feeds);
        this.errors = new ArrayList<>(errors);
    }

    public List<SyndFeed> getFeeds() {
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
        StringJoiner sj = new StringJoiner(", ", "[SyndFilterEvent ", "]");
        sj.add(String.format("feeds:%s", (feeds == null) ? "null" : feeds.size()));
        sj.add(String.format("errors:%s", (errors == null) ? "null" : errors.size()));
        return sj.toString();
    }
}
