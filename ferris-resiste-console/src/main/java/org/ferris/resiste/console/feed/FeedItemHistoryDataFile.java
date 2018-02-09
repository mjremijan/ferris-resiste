package org.ferris.resiste.console.feed;

import java.io.File;
import javax.inject.Inject;
import org.ferris.resiste.console.conf.ConfDirectory;
import org.ferris.resiste.console.data.DataDirectory;

/**
 * This is a hard coded {@link File} object to "data/feeds.txt"
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class FeedItemHistoryDataFile extends File {

    private static final long serialVersionUID = 140988098234509827L;

    /**
     * To file "data/feed_item_history.dat"
     *
     * @param datadir An {@link ConfDirectory}
     */
    @Inject
    public FeedItemHistoryDataFile(DataDirectory datadir) {
        super(datadir, String.format("feed_item_history.dat"));
        try {
            super.createNewFile();
        } catch (Exception e) {
            throw new RuntimeException(
                  String.format("Problem creating the data file \"%s\"", super.getAbsolutePath())
                , e
            );
        }
    }
}
