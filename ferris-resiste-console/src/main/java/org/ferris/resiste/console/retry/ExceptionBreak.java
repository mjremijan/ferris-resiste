package org.ferris.resiste.console.retry;

import jakarta.enterprise.inject.Vetoed;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class ExceptionBreak extends RuntimeException {

    private static final long serialVersionUID = 17609257745989856L;

    public ExceptionBreak(String msg) {
        super(msg);
    }
}
