package org.ferris.resiste.console.rss;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.StringJoiner;

/**
 *
 * @author Michael
 */
abstract class RssConnection {
    protected URL url;

    protected RssConnection(URL url) {
        this.url = url;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[" + getClass().getName() + " ", "]");
        sj.add(String.format("url:\"%s\"", (url == null) ? "null" : url));
        return sj.toString();
    }

    protected abstract InputStream getInputStream() throws IOException;
}
