package org.ferris.resiste.console.feed;

import java.util.Optional;
import javax.inject.Inject;
import org.apache.log4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class FeedFactory {

    @Inject
    protected Logger log;

    public Optional<Feed> toFeed(String commaSeparatedFeedData) {
        log.info("ENTER");

        commaSeparatedFeedData = commaSeparatedFeedData.trim();

        if (commaSeparatedFeedData.isEmpty()) {
            return Optional.empty();
        }

        if (commaSeparatedFeedData.startsWith("#")) {
            return Optional.empty();
        }

        String[] tokens
            = commaSeparatedFeedData.split(",");

        if (tokens.length != 2) {
            throw new RuntimeException(
                String.format("Token length is %d, not 2: \"%s\"", tokens.length, commaSeparatedFeedData)
            );
        }

        String id = tokens[0].trim();
        if (id.isEmpty()) {
            throw new RuntimeException(
                String.format("ID trimmed to empty: \"%s\"", commaSeparatedFeedData)
            );
        }

        String url = tokens[1].trim();
        if (url.isEmpty()) {
            throw new RuntimeException(
                String.format("URL trimmed to empty: \"%s\"", commaSeparatedFeedData)
            );
        }

        return Optional.of(new Feed(id, url));
    }
}
