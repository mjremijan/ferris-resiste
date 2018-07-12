package org.ferris.resiste.console.rss;

import java.util.List;
import javax.enterprise.inject.Vetoed;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class RssFeed {
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    protected String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    protected String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    protected List<RssEntry> entries;

    public List<RssEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<RssEntry> entries) {
        this.entries = entries;
    }

}
