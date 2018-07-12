package org.ferris.resiste.console.exit;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExitTool {

    public void exitAbnormal() {
        System.exit(1);
    }

    public void exitNormal() {
        System.exit(0);
    }
}
