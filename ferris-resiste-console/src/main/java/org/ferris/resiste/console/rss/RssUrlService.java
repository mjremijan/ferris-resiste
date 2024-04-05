package org.ferris.resiste.console.rss;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class RssUrlService {

    @Inject
    protected Logger log;

    @Inject
    protected RssUrlRepository repository;


    public List<RssUrl> findAll() {
        return repository.findAll();
    }
}
