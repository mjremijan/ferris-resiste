package org.ferris.resiste.console.rss;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class RssMediaFile {
    protected String type;

    public String getType() {
        return type;
    }

    protected String url;

    public String getUrl() {
        return url;
    }

    public RssMediaFile(String type, String url) {
        this.type = type;
        this.url = url;
    }
}
