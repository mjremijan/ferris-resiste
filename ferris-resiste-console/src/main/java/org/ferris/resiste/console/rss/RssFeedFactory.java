package org.ferris.resiste.console.rss;

import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.ferris.resiste.console.lang.StringUtils;
import org.ferris.resiste.console.retry.ExceptionRetry;
import org.jdom2.Element;
import org.slf4j.Logger;

/**
 * RssFeedFactory
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class RssFeedFactory {

    @Inject
    protected Logger log;

    @ExceptionRetry
    public RssFeed build(RssUrl feedUrl) throws IOException, FeedException {
        log.debug(String.format("ENTER %s", feedUrl));

        String rawXml = "RAW_XML";

        try {

            URLConnection connection = feedUrl.getUrl().openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();

            rawXml
                = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8")).lines().collect(Collectors.joining("\n"));

            com.rometools.rome.feed.synd.SyndFeed romeFeed
                = new SyndFeedInput().build(new XmlReader(new ByteArrayInputStream(rawXml.getBytes("UTF-8"))));

            List<com.rometools.rome.feed.synd.SyndEntry> romeEntries
                = romeFeed.getEntries();

            RssFeed feed = new RssFeed();
            feed.setId(feedUrl.getId());
            feed.setLink(romeFeed.getLink());
            feed.setTitle(romeFeed.getTitle());

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
                        } else if (re.getDescription() != null) {
                            sp.append(re.getDescription().getValue());
                        }
                        e.setContents(sp.toString());

                        return e;
                    }).collect(Collectors.toCollection(LinkedList::new))
            );

            return feed;
        } catch (FeedException e) {
            log.error(String.format("Error parsing RSS feed \"%s\"", feedUrl.getUrl().toString()));
            log.error(String.format("%nRAW_XML%n%s", rawXml));
            throw new FeedException(
                String.format("URL=\"%s\", RAW_XML=\"%s\"", feedUrl.getUrl().toString(), rawXml),
                 e);
        }
    }
}
