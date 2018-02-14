package org.ferris.resiste.console.rss;


import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.ferris.resiste.console.conf.ConfDirectory;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class RssUrlService {

    @Inject
    protected Logger log;

    @Inject
    protected RssUrlFactory factory;

    protected File dataFile;

    @Inject
    public RssUrlService(ConfDirectory confdir) {
        dataFile = new File(confdir, String.format("rss_urls.csv"));
        if (!dataFile.exists()) {
            throw new RuntimeException(
                String.format("Data file does not exist: \"%s\"", dataFile.getAbsolutePath())
            );
        }
    }

    public List<RssUrl> findAll() {
        log.info("ENTER");

        List<String> lines = null;

        try {
            lines = Files.readAllLines(dataFile.toPath());
        } catch (Exception e) {
            throw new RuntimeException(
                String.format("Problem reading file \"%s\"", dataFile.getAbsolutePath()), e
            );
        }

        List<RssUrl> urls =
            lines.stream()
                .map(s -> factory.parse(s))
                .filter(o -> o.isPresent())
                .map(o -> o.get())
                .collect(Collectors.toList())
            ;

        return urls;
    }
}
