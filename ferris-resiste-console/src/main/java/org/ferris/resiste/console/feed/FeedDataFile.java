package org.ferris.resiste.console.feed;

import java.io.File;
import javax.inject.Inject;
import org.ferris.resiste.console.conf.ConfDirectory;
import org.ferris.resiste.console.io.AbstractPropertiesFile;

/**
 * This is a hard coded {@link File} object to "data/feeds.txt"
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class FeedDataFile extends AbstractPropertiesFile {

    private static final long serialVersionUID = 140988098234509827L;

    /**
     * To file "conf/feeds.csv"
     *
     * @param confdir An {@link ConfDirectory}
     */
    @Inject
    public FeedDataFile(ConfDirectory confdir) {
        super(confdir, String.format("feeds.csv"));
    }
}
