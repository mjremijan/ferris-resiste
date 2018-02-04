package org.ferris.resiste.console.splash;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.ferris.resiste.console.main.StartupEvent;
import static org.ferris.resiste.console.main.StartupEvent.SPASH_SCREEN;
import org.jboss.weld.experimental.Priority;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class SplashScreenObserver {

    @Inject
    protected Logger log;

    @Inject
    protected SplashScreenPage splashScreenPage;

    public void observes(
            @Observes @Priority(SPASH_SCREEN) StartupEvent event
    ) {
        log.info("SplashScreen startup configuration observer");
        splashScreenPage.view();
    }
}
