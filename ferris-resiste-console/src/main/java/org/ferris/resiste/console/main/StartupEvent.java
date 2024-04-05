package org.ferris.resiste.console.main;

import jakarta.enterprise.inject.Vetoed;
import java.util.StringJoiner;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class StartupEvent {

    public static final int EXCEPTION = 10;

    public static final int SPASH_SCREEN = 30;

    public StartupEvent() {}

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", "[StartupEvent", "]");
        return sj.toString();
    }
}
