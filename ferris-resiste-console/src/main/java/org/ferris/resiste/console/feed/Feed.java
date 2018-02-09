package org.ferris.resiste.console.feed;

import java.net.URL;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class Feed {
    protected String id;
    protected URL url;

    public Feed(String id, String url) {
        this.id = id;
        try {
            this.url = new URL(url);
        } catch (Exception e) {
            throw new RuntimeException(
                  String.format("Problem creating a URL object for \"%s\"", url)
                , e
            );
        }
    }

    public String getId() {
        return id;
    }

    public URL getUrl() {
        return url;
    }

}
