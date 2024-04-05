package org.ferris.resiste.console.exception;

import jakarta.enterprise.context.ApplicationScoped;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * This class exists only for unit testing that the static method
 * Thread.setDefaultUncaughtExceptionHandler() is called.
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class UncaughtExceptionTool {

    public void setDefaultUncaughtExceptionHandler(UncaughtExceptionHandler handler) {
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }

}
