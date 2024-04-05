package org.ferris.resiste.console.lang;

import jakarta.enterprise.inject.Vetoed;
import java.util.Arrays;
import java.util.Optional;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static Optional<String> firstTrimToNonNull(String...strings) {
        return
            Arrays.asList(strings).stream()
                .map(s -> trimToNull(s))
                .filter(s -> s != null)
                .findFirst();
    }

    public static String abbreviate(String str, int maxWidth) {
        return org.apache.commons.lang3.StringUtils.abbreviate(str, maxWidth);
    }
}
