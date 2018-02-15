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
public class RssUrlRepository extends File {

    private static final long serialVersionUID = 7491906484654964631L;

    @Inject
    protected Logger log;

    @Inject
    protected RssUrlFactory factory;


    @Inject
    public RssUrlRepository(ConfDirectory root) {
        super(root, String.format("rss_urls.csv"));
        if (!super.exists()) {
            throw new RuntimeException(
                String.format("Data file does not exist: \"%s\"", super.getAbsolutePath())
            );
        }
    }


    public List<RssUrl> findAll() {
        log.info("ENTER");

        List<String> lines = null;

        try {
            lines = Files.readAllLines(super.toPath());
        } catch (Exception e) {
            throw new RuntimeException(
                String.format("Problem reading file \"%s\"", super.getAbsolutePath()), e
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
