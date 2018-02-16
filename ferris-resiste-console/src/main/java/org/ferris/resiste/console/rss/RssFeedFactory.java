package org.ferris.resiste.console.rss;

import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.ferris.resiste.console.lang.StringUtils;
import org.ferris.resiste.console.retry.ExceptionBreak;
import org.ferris.resiste.console.retry.ExceptionRetry;
import org.jdom2.Element;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class RssFeedFactory {

    @Inject
    protected Logger log;

    @ExceptionRetry
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
                    () -> new ExceptionBreak("Oldest published date not found"))
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

                    // Enclosures
                    Optional<List<SyndEnclosure>> enclosures
                        = Optional.ofNullable(re.getEnclosures());
                    // Foreign markup
                    Optional<List<Element>> foreignMarkups
                        = Optional.ofNullable(re.getForeignMarkup());


                    // Images
                    enclosures.ifPresent(
                        se -> se.stream().filter(a -> a.getType().toLowerCase().startsWith("image")).forEach(
                            b -> e.addImage(new RssImage(b.getType(), b.getUrl())))
                    );
                    foreignMarkups.ifPresent(
                        fm -> fm.stream()
                            .filter(el -> el.getName().toLowerCase().equals("thumbnail"))
                            .map(el -> el.getAttribute("url"))
                            .filter(at -> at != null)
                            .map(at -> StringUtils.trimToNull(at.getValue()))
                            .filter(s -> s != null)
                            .forEach(s -> e.addImage(new RssImage("image/thumbnail", s)))
                    );

                    // Other media files
                    enclosures.ifPresent(
                        se -> se.stream().filter(a -> !a.getType().toLowerCase().startsWith("image")).forEach(
                            b -> e.addMediaFile(new RssMediaFile(b.getType(), b.getUrl())))
                    );


                    // Content
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
