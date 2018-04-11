package org.ferris.resiste.console.rss;


import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class RssUrlService {

    @Inject
    protected Logger log;

    @Inject
    protected RssUrlRepository repository;


    public List<RssUrl> findAll() {
        return repository.findAll();
    }
}
