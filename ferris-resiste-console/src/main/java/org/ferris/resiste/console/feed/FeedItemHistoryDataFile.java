package org.ferris.resiste.console.feed;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
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
    }

    public boolean exists(String feedId, String itemId) {
        boolean found = false;

        try (
            // Open data file for reading
            Scanner input = new Scanner(this);
        ) {
            // Loop over each line
            while (!found && input.hasNext()) {

                // Read the line of data from the data file
                String next = input.next();

                // Empty line? typically the last line
                if (next.isEmpty()) {
                    continue;
                }

                // Data is tab-delimited, so split
                String[] tokens = next.split("\t");

                // See line is a match to given feedId and itemId
                found =
                    tokens[0].equals(feedId)
                    &&
                    tokens[1].equals(itemId)
                ;
            }
        } catch (Exception e) {
            throw new RuntimeException(
                  String.format("Problem checking if feedId=\"%s\", itemId\"%s\" exists. File=%s", feedId, itemId, this.getAbsolutePath())
                , e
            );
        }

        // Return if found or not
        return found;
    }


    public void store(String feedId, String itemId) {
        try (
            // Open data file for appending
            PrintWriter writer = new PrintWriter(
                new FileOutputStream(this, true)
            );
        ) {
            writer.printf("%s\t%s%n", feedId, itemId);
            writer.flush();
        } catch (Exception e) {
            throw new RuntimeException(
                  String.format("Problem appending feedId=\"%s\", itemId\"%s\". File=%s", feedId, itemId, this.getAbsolutePath())
                , e
            );
        }
    }
}
