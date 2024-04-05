package org.ferris.resiste.console.text.i18n;

import jakarta.enterprise.inject.Vetoed;

@Vetoed
public class LocalizedString {

    private final String string;

    public static LocalizedString format(String fmt, String...args) {
        return new LocalizedString(
            String.format(fmt, args)
        );
    }

    public LocalizedString(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }

    public int length() {
        return toString().length();
    }

    public int intValue(int defaultValue) {
        try {
            return Integer.parseInt(string);
        } catch (Throwable t) {
            return defaultValue;
        }
    }
}
