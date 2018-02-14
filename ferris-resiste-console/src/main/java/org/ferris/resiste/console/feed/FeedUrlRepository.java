package org.ferris.resiste.console.feed;


import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.ferris.resiste.console.conf.ConfDirectory;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class FeedUrlRepository {

    @Inject
    protected Logger log;

    @Inject
    protected FeedItemHistoryDataFile itemHistoryData;

    @Inject
    protected FeedFactory factory;

    protected File feedData;

    @Inject
    public void FeedUrlRepository(ConfDirectory confdir) {
        feedData = new File(confdir, String.format("feeds.csv"));
        if (!feedData.exists()) {
            throw new RuntimeException(
                String.format("Data file does not exist: \"%s\"", feedData.getAbsolutePath())
            );
        }
    }

    public boolean findItemInHistory(String feedId, String itemId) {
        log.info(String.format("ENTER \"%s\", \"%s\"", feedId, itemId));
        boolean found = false;

        try (
            // Open data file for reading
            Scanner input = new Scanner(itemHistoryData);
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
                    tokens[1].equals(itemId)
                ;
            }
        } catch (Exception e) {
            throw new RuntimeException(
                  String.format("Problem checking if feedId=\"%s\", itemId\"%s\" exists. File=%s", feedId, itemId, itemHistoryData.getAbsolutePath())
                , e
            );
        }

        // Return if found or not
        return found;
    }


    public void storeItemInHistory(String feedId, String itemId) {
        log.info(String.format("ENTER \"%s\", \"%s\"", feedId, itemId));

        try (
            // Open data file for appending
            PrintWriter writer = new PrintWriter(
                new FileOutputStream(itemHistoryData, true)
            );
        ) {
            writer.printf("%s\t%s%n", feedId, itemId);
            writer.flush();
        } catch (Exception e) {
            throw new RuntimeException(
                  String.format("Problem appending feedId=\"%s\", itemId\"%s\". File=%s", feedId, itemId, itemHistoryData.getAbsolutePath())
                , e
            );
        }
    }


    public List<FeedUrl> findAll() {
        log.info("ENTER");

        List<String> lines = null;

        try {
            lines = Files.readAllLines(feedData.toPath());
        } catch (Exception e) {
            throw new RuntimeException(
                String.format("Problem reading file \"%s\"", feedData.getAbsolutePath()), e
            );
        }

        List<FeedUrl> feeds =
            lines.stream()
                .map(s -> factory.toFeed(s))
                .filter(o -> o.isPresent())
                .map(o -> o.get())
                .collect(Collectors.toList())
            ;

        return feeds;
    }
}
