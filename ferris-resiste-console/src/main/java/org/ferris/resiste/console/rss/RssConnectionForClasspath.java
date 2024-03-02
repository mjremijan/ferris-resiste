package org.ferris.resiste.console.rss;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 *
 * @author Michael
 */
class RssConnectionForClasspath extends RssConnection {

    protected RssConnectionForClasspath(URL url) {
        super(url);
    }

    @Override
    protected InputStream getInputStream() throws IOException {
        return url.openConnection().getInputStream();
    } 
}
