package org.ferris.resiste.console.email;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import org.ferris.resiste.console.rome.SyndFeed;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailSendEvent {

    public static final int DRAFT_MAP = 100;
    public static final int DRAFT_VIEW = 200;
    public static final int SEND = 300;

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[EmailSendEvent ", "]");
        sj.add(String.format("feeds:%s", (feeds == null) ? "null" : feeds.size()));
        sj.add(String.format("drafts:%s", (drafts == null) ? "null" : drafts.size()));
        return sj.toString();
    }

    protected List<SyndFeed> feeds;
    protected List<EmailDraft> drafts;

    public EmailSendEvent(List<SyndFeed> feeds) {
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
