package org.ferris.resiste.console.exception;

import java.lang.Thread.UncaughtExceptionHandler;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.ferris.resiste.console.exit.ExitEvent;
import org.ferris.resiste.console.main.StartupEvent;
import static org.ferris.resiste.console.main.StartupEvent.EXCEPTION;
import org.jboss.weld.experimental.Priority;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class UncaughtExceptionObserver implements UncaughtExceptionHandler {

    @Inject
    protected Logger log;

    @Inject
    protected UncaughtExceptionTool exceptionTool;

    @Inject
    protected UncaughtExceptionPage uncaughtExceptionPage;

    @Inject
    protected Event<ExitEvent> exitEvent;

    public void observes(
            @Observes @Priority(EXCEPTION) StartupEvent event
    ) {
        log.info("UncaughtExceptionObserver startup configuration observer");
        exceptionTool.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        uncaughtExceptionPage.view(e);
        exitEvent.fire(new ExitEvent());
    }
}
