package org.ferris.resiste.console.rss;

import java.util.Optional;
import java.util.regex.Matcher;
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

        Pattern p 
            = Pattern.compile("([^,]+),([^,]+)(,\\s*regex\\[\\[(.+)\\]\\]\\s*)?$", Pattern.MULTILINE);
        //    ^ asserts position at start of a line
        //    1st Capturing Group ([^,]+)
        //    Match a single character not present in the list below [^,]
        //    + matches the previous token between one and unlimited times, as many times as possible, giving back as needed (greedy)
        //    , matches the character , with index 4410 (2C16 or 548) literally (case sensitive)
        //    , matches the character , with index 4410 (2C16 or 548) literally (case sensitive)
        //    2nd Capturing Group ([^,]+)
        //    Match a single character not present in the list below [^,]
        //    + matches the previous token between one and unlimited times, as many times as possible, giving back as needed (greedy)
        //    , matches the character , with index 4410 (2C16 or 548) literally (case sensitive)
        //    3rd Capturing Group (,\s*+regex\[\[(.+)\]\])?
        //    ? matches the previous token between zero and one times, as many times as possible, giving back as needed (greedy)
        //    , matches the character , with index 4410 (2C16 or 548) literally (case sensitive)
        //    \s matches any whitespace character (equivalent to [\r\n\t\f\v ])
        //    *+ matches the previous token between zero and unlimited times, as many times as possible, without giving back (possessive)
        //    regex matches the characters regex literally (case sensitive)
        //    \[ matches the character [ with index 9110 (5B16 or 1338) literally (case sensitive)
        //    \[ matches the character [ with index 9110 (5B16 or 1338) literally (case sensitive)
        //    4th Capturing Group (.+)
        //    . matches any character (except for line terminators)
        //    + matches the previous token between one and unlimited times, as many times as possible, giving back as needed (greedy)
        //    \] matches the character ] with index 9310 (5D16 or 1358) literally (case sensitive)
        //    \] matches the character ] with index 9310 (5D16 or 1358) literally (case sensitive)
        //    $ asserts position at the end of a line
        Matcher m = p.matcher(commaSeparatedFeedData);
        
        if (!m.matches()) {
            throw new RuntimeException(
                String.format("Line \"%s\" does not match regex pattern \"%s\"", commaSeparatedFeedData, p.pattern())
            );
        }
                         
        // Regex pattern ensures this capturing group can't be null
        String id = m.group(1).trim();
        if (id.isEmpty()) {
            throw new RuntimeException(
                String.format("ID trimmed to empty: \"%s\"", commaSeparatedFeedData)
            );
        }

        // Regex pattern ensures this capturing group can't be null
        String url = m.group(2).trim();
        if (url.isEmpty()) {
            throw new RuntimeException(
                String.format("URL trimmed to empty: \"%s\"", commaSeparatedFeedData)
            );
        }
        
        Optional<Pattern> pattern =  Optional.empty();   
        // This regex pattern capturing group may be null
        String userPattern = m.group(4);
        if (userPattern != null) {            
            try {
                pattern = Optional.of(Pattern.compile(userPattern));
            } catch (Exception e) {
                throw new RuntimeException(
                    String.format("User defined regex pattern \"%s\" failed to compile", userPattern)
                );
            }
        }

        return Optional.of(new RssUrl(id, url, pattern));
    }
}
