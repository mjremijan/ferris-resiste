package org.ferris.resiste.console.rss;

import java.util.List;
import javax.enterprise.inject.Vetoed;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class RssFeed {
    
    private static String trim(String in) {
        return (in == null) ? null : in.trim();
    }
    
    protected String id;

    public String getId() {
        return id;
    }
    
    public void setId(String id) {     
        this.id = trim(id);
    }

    protected String title;

    public String getTitle() {
        return trim(title);
    }

    public void setTitle(String title) {
        this.title = trim(title);
    }

    protected String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = trim(link);
    }

    protected List<RssEntry> entries;

    public List<RssEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<RssEntry> entries) {
        this.entries = entries;
    }

}
