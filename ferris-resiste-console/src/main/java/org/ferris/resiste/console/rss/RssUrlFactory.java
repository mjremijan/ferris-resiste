package org.ferris.resiste.console.rss;

import java.util.Optional;
import java.util.regex.Pattern;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.ferris.resiste.console.lang.StringUtils;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class RssUrlFactory {

    @Inject
    protected Logger log;

    public Optional<RssUrl> parse(String commaSeparatedFeedData) {
        log.info(String.format("ENTER \"%s\"", commaSeparatedFeedData));

        commaSeparatedFeedData = StringUtils.trimToEmpty(commaSeparatedFeedData);

        if (commaSeparatedFeedData.isEmpty()) {
            return Optional.empty();
        }

        if (commaSeparatedFeedData.startsWith("#")) {
            return Optional.empty();
        }

        String[] tokens
            = commaSeparatedFeedData.split(",");
        // ^([^,]+),([^,]+)(,regex\[\[(.+)\]\])?$

        if (tokens.length != 2 && tokens.length != 3) {
            throw new RuntimeException(
                String.format("Token length is %d which is not 2 or 3: \"%s\"", tokens.length, commaSeparatedFeedData)
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
        
        Optional<Pattern> match =  Optional.empty();
        if (tokens.length == 3) {
            String s = tokens[2].trim();
            if (!id.isEmpty()) {
                try {
                    match = Optional.of(Pattern.compile(s));
                } catch (Exception e) {
                    throw new RuntimeException(
                        String.format("Regex pattern \"%s\" failed to compile", s)
                    );
                }
            }
        }

        return Optional.of(new RssUrl(id, url, match));
    }
}
