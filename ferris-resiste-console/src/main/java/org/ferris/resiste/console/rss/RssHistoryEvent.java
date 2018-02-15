package org.ferris.resiste.console.rss;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import org.ferris.resiste.console.email.*;
import org.ferris.resiste.console.rome.SyndFeed;

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

    protected List<SyndFeed> feeds;
    protected List<EmailDraft> drafts;

    public RssHistoryEvent(List<SyndFeed> feeds) {
        this.feeds = new ArrayList<>(feeds);
    }

    public List<SyndFeed> getFeeds() {
        return feeds;
    }

    public List<EmailDraft> getDrafts() {
        return drafts;
    }

    public void setDrafts(List<EmailDraft> drafts) {
        this.drafts = drafts;
    }
}
