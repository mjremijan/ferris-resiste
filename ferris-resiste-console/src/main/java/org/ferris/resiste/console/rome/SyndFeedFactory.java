package org.ferris.resiste.console.rome;

import org.ferris.resiste.console.rss.RssEntry;
import org.ferris.resiste.console.rss.RssFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.ferris.resiste.console.rss.RssUrl;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class SyndFeedFactory {

    @Inject
    protected Logger log;

    public RssFeed build(RssUrl feedUrl) throws IOException, FeedException {
        log.info(String.format("ENTER %s", feedUrl));

        com.rometools.rome.feed.synd.SyndFeed romeFeed
            = new SyndFeedInput().build(new XmlReader(feedUrl.getUrl()));

        List<com.rometools.rome.feed.synd.SyndEntry> romeEntries
            = romeFeed.getEntries();

        RssFeed feed = new RssFeed();
        feed.setId(feedUrl.getId());
        feed.setLink(romeFeed.getLink());
        feed.setTitle(romeFeed.getTitle());

        feed.setOldestPublishedDate(
            romeEntries.stream()
                .map(re -> re.getPublishedDate())
                .filter(d -> d != null)
                // An Instant is an actual point in time, expressed using UTC – a universal time scale
                .map(d -> d.toInstant())
                .min((i1, i2) -> i1.compareTo(i2))
                .orElseThrow(
                    () -> new RuntimeException("Oldest published date not found"))
        );

        feed.setEntries(
            romeEntries.stream()
                .map(re -> {
                    RssEntry e = new RssEntry();
                    e.setGuid(re.getUri());
                    e.setTitle(re.getTitle());
                    e.setAuthor(re.getAuthor());
                    e.setLink(re.getLink());
                    e.setPublishedDate(re.getPublishedDate());

                    StringBuilder sp = new StringBuilder("");
                    if (re.getContents() != null && !re.getContents().isEmpty()) {
                        re.getContents().stream()
                            .forEach(sc -> sp.append(sc.getValue()));
                    }
                    else
                    if (re.getDescription() != null) {
                        sp.append(re.getDescription().getValue());
                    }
                    e.setContents(sp.toString());

                    return e;
                }).collect(Collectors.toList())
        );

        return feed;
    }
}
