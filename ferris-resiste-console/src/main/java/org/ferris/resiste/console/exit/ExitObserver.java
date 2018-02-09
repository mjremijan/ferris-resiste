package org.ferris.resiste.console.exit;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.slf4j.Logger;

public class ExitObserver {

    @Inject
    protected Logger log;

    @Inject
    protected ExitTool systemTool;

    @Inject
    protected ExitPage exitPage;

    public void observes(@Observes ExitEvent exitEvent) {
        log.info("View page");
        exitPage.view();
        log.info("Exit JVM");
        systemTool.exitAbnormal();
    }

}
