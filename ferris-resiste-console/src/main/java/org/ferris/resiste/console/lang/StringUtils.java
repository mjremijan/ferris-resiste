package org.ferris.resiste.console.lang;

import java.util.Arrays;
import java.util.Optional;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static Optional<String> firstTrimToNonNull(String...strings) {
        return
            Arrays.asList(strings).stream()
                .map(s -> trimToNull(s))
                .filter(s -> s != null)
                .findFirst();
    }
}
