package org.ferris.resiste.console.email;

import com.rometools.rome.feed.synd.SyndFeed;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailSendEvent {

    public static final int DRAFT_MAP = 100;
    public static final int DRAFT_VIEW = 110;
    public static final int SEND = 9999;

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[EmailSendEvent ", "]");
        sj.add(String.format("feeds:%s", (feeds == null) ? "null" : feeds.size()));
        sj.add(String.format("errors:%s", (errors == null) ? "null" : errors.size()));
        sj.add(String.format("drafts:%s", (drafts == null) ? "null" : drafts.size()));
        return sj.toString();
    }

    protected List<String> errors;
    protected List<SyndFeed> feeds;
    protected List<EmailDraft> drafts;

    public EmailSendEvent(List<SyndFeed> feeds, List<String> errors) {
        this.feeds = new ArrayList<>(feeds);
        this.errors = new ArrayList<>(errors);
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

    public void addError(String msg) {
        errors.add(msg);
    }
}
