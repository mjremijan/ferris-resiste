package org.ferris.resiste.console.rss;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class RssHistoryEvent {

    public static final int STORE = 100;

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[RssHistoryEvent ", "]");
        sj.add(String.format("feeds:%s", (feeds == null) ? "null" : feeds.size()));
        return sj.toString();
    }

    protected List<RssFeed> feeds;

    public RssHistoryEvent(List<RssFeed> feeds) {
        this.feeds = new ArrayList<>(feeds);
    }

    public List<RssFeed> getFeeds() {
        return feeds;
    }
}
