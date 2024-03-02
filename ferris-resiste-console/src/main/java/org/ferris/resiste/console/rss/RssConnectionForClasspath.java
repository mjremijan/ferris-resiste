package org.ferris.resiste.console.rss;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Michael
 */
class RssConnectionForClasspath extends RssConnection {

    protected RssConnectionForClasspath(RssUrl url) {
        super(url);
    }

    @Override
    protected InputStream getInputStream() throws IOException {
        return url.getUrl().openConnection().getInputStream();
    } 
}
