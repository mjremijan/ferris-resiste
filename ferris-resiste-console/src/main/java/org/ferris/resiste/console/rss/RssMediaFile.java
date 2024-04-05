package org.ferris.resiste.console.rss;

import jakarta.enterprise.inject.Vetoed;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
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
