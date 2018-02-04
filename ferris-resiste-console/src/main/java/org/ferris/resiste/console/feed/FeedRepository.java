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
    protected FeedDataFile file;

    @Inject
    protected FeedFactory factory;

    public boolean itemExists(String guid) {

    }

    public List<Feed> findAll() {
        log.info("ENTER");

        List<String> lines = null;

        try {
            lines = Files.readAllLines(file.toPath());
        } catch (Exception e) {
            throw new RuntimeException(
                String.format("Problem reading file \"%s\"", file.getAbsolutePath()), e
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
