package org.ferris.resiste.console.feed;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class Feed {
    protected String id;
    protected String url;

    public Feed(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

}
