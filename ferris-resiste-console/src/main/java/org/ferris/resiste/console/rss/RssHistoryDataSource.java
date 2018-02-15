package org.ferris.resiste.console.rss;

import java.io.File;
import javax.inject.Inject;
import org.ferris.resiste.console.data.DataDirectory;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class RssHistoryDataSource extends File {

    private static final long serialVersionUID = 7491906484654964631L;

    @Inject
    public RssHistoryDataSource(DataDirectory root) {
        super(root, String.format("rss_history.dat"));
        try {
            super.createNewFile();
        } catch (Exception e) {
            throw new RuntimeException(
                String.format("Problem creating the data file \"%s\"", super.getAbsolutePath()),
                 e
            );
        }
    }
}
