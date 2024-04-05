package org.ferris.resiste.console.rss;

import jakarta.enterprise.inject.Vetoed;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class RssImage {
    protected String type;

    public String getType() {
        return type;
    }

    protected String url;

    public String getUrl() {
        return url;
    }

    public RssImage(String type, String url) {
        this.type = type;
        this.url = url;
    }
}
