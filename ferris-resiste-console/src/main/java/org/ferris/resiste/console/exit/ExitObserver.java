package org.ferris.resiste.console.exit;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
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
