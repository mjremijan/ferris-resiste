package org.ferris.resiste.console.rss;

import java.io.IOException;
import java.io.InputStream;
import java.util.StringJoiner;

/**
 *
 * @author Michael
 */
abstract class RssConnection {
    protected RssUrl url;

    protected RssConnection(RssUrl url) {
        this.url = url;
    }

    protected RssUrl getUrl() {
        return url;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[" + getClass().getName() + " ", "]");
        sj.add(String.format("url:\"%s\"", (url == null) ? "null" : url));
        return sj.toString();
    }

    protected abstract InputStream getInputStream() throws IOException;
}
