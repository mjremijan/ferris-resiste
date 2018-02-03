package org.ferris.resiste.console.text.i18n;

public class LocalizedString {

    private String string;

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
