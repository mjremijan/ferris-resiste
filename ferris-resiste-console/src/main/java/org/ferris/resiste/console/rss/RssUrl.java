package org.ferris.resiste.console.rss;

import java.net.URL;
import java.util.StringJoiner;
import javax.enterprise.inject.Vetoed;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class RssUrl {
    protected String id;
    protected URL url;
    protected RssConnection connection;

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[RssUrl ", "]");
        sj.add(String.format("id:%s", (id == null) ? "null" : id));
        sj.add(String.format("url:%s", (url == null) ? "null" : url));
        sj.add(String.format("connection:%s", (connection == null) ? "null" : connection));
        return sj.toString();
    }

    public RssUrl(String id, String url) {
        this.id = id;
        
        if (url.startsWith("classpath://")) {
            url = url.substring(12);
            this.url = getClass().getClassLoader().getResource(url);
            if (this.url == null) {
                throw new RuntimeException("Resource not found \"" +url+ "\"");
            }
            connection = new RssConnectionForClasspath(this.url);
        } 
        else {
            try {
                this.url = new URL(url);
                connection = new RssConnectionForHttp(this.url);
            } catch (Exception e) {
                throw new RuntimeException(
                      String.format("Problem creating a URL object for \"%s\"", url)
                    , e
                );
            }
        }
        
    }

    public String getId() {
        return id;
    }

    public URL getUrl() {
        return url;
    }
    
    public RssConnection openConnection() {        
        return connection;
    }

}
