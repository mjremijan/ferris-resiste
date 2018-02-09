package org.ferris.resiste.console.util.version;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class Version {

    public static final String UNKOWN = "<unknown>";

    protected Package getPackage() {
        Package p = null;
        try {
            p = Package.getPackage(getClass().getPackage().getName());
        } catch (Throwable t) {
            // do nothing
        }
        return p;
    }

    /**
     * @return {@link Package#getImplementationTitle() }
     */
    public String getImplementationTitle() {
        String s = "";
        Package pack = getPackage();
        if (pack != null) {
            s = StringUtils.trimToEmpty(pack.getImplementationTitle());
        }
        if (s.isEmpty()) {
            s = UNKOWN;
        }
        return s;
    }

    /**
     * @return {@link Package#getImplementationVersion() }
     */
    public String getImplementationVersion() {
        String s = "";
        Package pack = getPackage();
        if (pack != null) {
            s = StringUtils.trimToEmpty(pack.getImplementationVersion());
        }
        if (s.isEmpty()) {
            s = UNKOWN;
        }
        return s;
    }

    /**
     * @return {@link Package#getImplementationVendor() }
     */
    public String getImplementationVendor() {
        String s = "";
        Package pack = getPackage();
        if (pack != null) {
            s = StringUtils.trimToEmpty(pack.getImplementationVendor());
        }
        if (s.isEmpty()) {
            s = UNKOWN;
        }
        return s;
    }
}
