package org.ferris.resiste.console.rss;

import java.io.File;
import javax.inject.Inject;
import org.ferris.resiste.console.conf.ConfDirectory;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class RssUrlDataSource extends File {

    private static final long serialVersionUID = 7491906484654964631L;

    @Inject
    public RssUrlDataSource(ConfDirectory root) {
        super(root, String.format("rss_urls.csv"));
        if (!super.exists()) {
            throw new RuntimeException(
                String.format("Data file does not exist: \"%s\"", super.getAbsolutePath())
            );
        }
    }
}
