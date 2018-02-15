package org.ferris.resiste.console.rss;

import java.io.File;
import java.time.Instant;
import java.util.Formatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.ferris.resiste.console.data.DataDirectory;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class RssHistoryRepository extends File {

    private static final long serialVersionUID = 7491906484654964631L;

    @Inject
    protected Logger log;

    protected Map<String, List<RssHistory>> cache;

    @Inject
    public RssHistoryRepository(DataDirectory root) {
        super(root, String.format("rss_history.dat"));
        try {
            super.createNewFile();
        } catch (Exception e) {
            throw new RuntimeException(
                String.format("Problem creating the data file \"%s\"", super.getAbsolutePath()),
                 e
            );
        }
    }

    public Optional<RssHistory> find(String feedId, String entryId)
    {
        log.info(String.format("ENTER \"%s\", \"%s\"", feedId, entryId));

        Optional<RssHistory> retval = Optional.empty();

        if (cache.containsKey(feedId)) {
            retval = cache.get(feedId)
                .stream()
                .filter(h -> h.getEntryId().equals(entryId))
                .findFirst()
            ;
        }

        return retval;
    }


    @PostConstruct
    public void postConstruct() {
        log.info("ENTER");

        log.debug("Create data structure for cache");
        cache = new HashMap<>();

        log.debug("Open data file for reading");
        try (
            Scanner input = new Scanner(this);
        ) {
            log.debug("Loop over each line");
            while (input.hasNext()) {

                log.debug("Read the line of data from the data file");
                String next = input.nextLine();
                log.debug(String.format("LINE: \"%s\"", next));

                log.debug("Empty line? typically the last line");
                if (next.isEmpty()) {
                    continue;
                }

                log.debug("Split the line of data by tab");
                String[] tokens = next.split("\\t");
                if (tokens.length != 3) {
                    throw new Exception(
                        String.format("Line does not have 3 tokens \"%s\"", next)
                    );
                }

                log.debug("Create new RssHistory");
                RssHistory h = new RssHistory(
                      tokens[0]                // feedId
                    , tokens[1]                // entryId
                    , Instant.parse(tokens[2]) // publishedDate
                );

                log.debug("Cache RssHistory in cache");
                this.cache(h);
            }
        } catch (Exception e) {
            throw new RuntimeException(
                  String.format("Problem building cache for data file=%s", this.getAbsolutePath())
                , e
            );
        }
    }

    private void cache(RssHistory h) {
        log.debug(String.format("ENTER %s", h));

        if (cache.containsKey(h.getFeedId())) {
            cache.get(h.getFeedId()).add(h);
        } else {
            List<RssHistory> hlist = new LinkedList<>();
            hlist.add(h);
            cache.put(h.getFeedId(), hlist);
        }
    }

    private void write() {
        log.info("ENTER");

        log.debug("Open data file for writing");
        try (
            Formatter formatter = new Formatter(this.getAbsolutePath(), "UTF-8");
        ) {
            log.debug("Loop over cache");
            cache.values().stream().forEach(hl -> hl.forEach(h ->
                formatter.format("%s\t%s\t%s%n"
                    , h.getFeedId(), h.getEntryId(), h.getPublished().toString()
                )
            ));
            formatter.flush();
        } catch (Exception e) {
            throw new RuntimeException(
                  String.format("Problem building cache for data file=%s", this.getAbsolutePath())
                , e
            );
        }
    }

    protected void store(RssHistory h) {
        log.info(String.format("ENTER %s", h));
        this.cache(h);
        this.write();
    }
}
