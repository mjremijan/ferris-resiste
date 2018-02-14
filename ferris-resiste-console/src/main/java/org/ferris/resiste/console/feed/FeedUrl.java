package org.ferris.resiste.console.feed;

import java.net.URL;
import java.util.StringJoiner;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class FeedUrl {
    protected String id;
    protected URL url;

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[FeedUrl ", "]");
        sj.add(String.format("id:%s", (id == null) ? "null" : id));
        sj.add(String.format("url:%s", (url == null) ? "null" : url));
        return sj.toString();
    }

    public FeedUrl(String id, String url) {
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
