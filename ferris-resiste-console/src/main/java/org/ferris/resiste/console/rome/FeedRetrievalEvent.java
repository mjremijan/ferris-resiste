package org.ferris.resiste.console.rome;

import com.rometools.rome.feed.synd.SyndFeed;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class FeedRetrievalEvent {

    public static final int RETRIEVE = 100;

    public static final int VIEW = 200;

    protected Map<String, String> errors;

    protected List<SyndFeed> feeds;

    public FeedRetrievalEvent() {
        errors = new HashMap<>();
    }

    public void setFeeds(List<SyndFeed> feeds) {
        this.feeds = feeds;
    }

    public List<SyndFeed> getFeeds() {
        return feeds;
    }

    public void addError(String key, String message) {
        errors.put(key, message);
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
