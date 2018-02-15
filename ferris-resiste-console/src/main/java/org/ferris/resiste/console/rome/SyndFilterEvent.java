package org.ferris.resiste.console.rome;

import org.ferris.resiste.console.rss.RssFeed;
import java.util.List;
import java.util.StringJoiner;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class SyndFilterEvent {

    public static final int FILTER = 100;
    public static final int VIEW = 200;

    protected List<RssFeed> feeds;

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[SyndFilterEvent ", "]");
        sj.add(String.format("feeds:%s", (feeds == null) ? "null" : feeds.size()));
        return sj.toString();
    }

    public SyndFilterEvent(List<RssFeed> feeds) {
        this.feeds = feeds;
    }

    public List<RssFeed> getFeeds() {
        return feeds;
    }
}
