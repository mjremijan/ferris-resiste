package org.ferris.resiste.console.rss;


import java.util.Scanner;
import javax.inject.Inject;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class RssHistoryService {

    @Inject
    protected Logger log;

    @Inject
    protected RssHistoryDataSource dataFile;

    public boolean exists(String feedId, String itemGuid) {
        log.info(String.format("ENTER \"%s\", \"%s\"", feedId, itemGuid));
        boolean found = false;

        try (
            // Open data file for reading
            Scanner input = new Scanner(dataFile);
        ) {
            // Loop over each line
            while (!found && input.hasNext()) {

                // Read the line of data from the data file
                String next = input.nextLine();
                log.debug(String.format("LINE: \"%s\"", next));

                // Empty line? typically the last line
                if (next.isEmpty()) {
                    continue;
                }

                // Data is tab-delimited, so split
                String[] tokens = next.split("\\t");

                // See if line is a match to given feedId and itemId
                found =
                    tokens[0].equals(feedId)
                    &&
                    tokens[1].equals(itemGuid)
                ;
            }
        } catch (Exception e) {
            throw new RuntimeException(
                  String.format("Problem checking if feedId=\"%s\", itemId\"%s\" exists. File=%s", feedId, itemGuid, dataFile.getAbsolutePath())
                , e
            );
        }

        // Return if found or not
        return found;
    }
}
