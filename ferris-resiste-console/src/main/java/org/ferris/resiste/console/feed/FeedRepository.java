package org.ferris.resiste.console.feed;


import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.apache.log4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class FeedRepository {

    @Inject
    protected Logger log;

    @Inject
    protected FeedDataFile feedData;

    @Inject
    protected FeedItemHistoryDataFile itemHistoryData;

    @Inject
    protected FeedFactory factory;

    public boolean findItemInHistory(String feedId, String itemId) {
        return itemHistoryData.exists(feedId, itemId);
    }

    public void storeItemInHistory(String feedId, String itemId) {
        itemHistoryData.store(feedId, itemId);
    }

    public List<Feed> findAll() {
        log.info("ENTER");

        List<String> lines = null;

        try {
            lines = Files.readAllLines(feedData.toPath());
        } catch (Exception e) {
            throw new RuntimeException(
                String.format("Problem reading file \"%s\"", feedData.getAbsolutePath()), e
            );
        }

        List<Feed> feeds =
            lines.stream()
                .map(s -> factory.toFeed(s))
                .filter(o -> o.isPresent())
                .map(o -> o.get())
                .collect(Collectors.toList())
            ;

        return feeds;
    }
}
