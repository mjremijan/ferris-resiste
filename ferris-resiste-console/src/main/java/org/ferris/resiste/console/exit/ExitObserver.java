package org.ferris.resiste.console.exit;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class ExitObserver {

    @Inject
    protected ExitTool systemTool;

    @Inject
    protected ExitPage exitPage;

    public void observes(@Observes ExitEvent exitEvent) {
        exitPage.view();
        systemTool.exitAbnormal();
    }

}
