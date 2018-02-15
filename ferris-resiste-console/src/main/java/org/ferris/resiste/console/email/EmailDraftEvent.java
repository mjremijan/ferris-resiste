package org.ferris.resiste.console.email;

import java.util.List;
import java.util.StringJoiner;
import org.ferris.resiste.console.rss.RssFeed;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailDraftEvent {

    public static final int DRAFT_MAP = 100;
    public static final int DRAFT_VIEW = 200;
    public static final int DRAFT_SEND = 300;

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[EmailDraftEvent ", "]");
        sj.add(String.format("feeds:%s", (feeds == null) ? "null" : feeds.size()));
        sj.add(String.format("drafts:%s", (drafts == null) ? "null" : drafts.size()));
        return sj.toString();
    }

    protected List<RssFeed> feeds;
    protected List<EmailDraft> drafts;

    public EmailDraftEvent(List<RssFeed> feeds) {
        this.feeds = feeds;
    }

    public List<RssFeed> getFeeds() {
        return feeds;
    }

    public List<EmailDraft> getDrafts() {
        return drafts;
    }

    public void setDrafts(List<EmailDraft> drafts) {
        this.drafts = drafts;
    }
}
