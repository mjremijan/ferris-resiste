package org.ferris.resiste.console.text.i18n;

import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;
import org.apache.commons.lang3.StringUtils;
import org.ferris.resiste.console.text.i18n.qualifier.Welcome;

@ApplicationScoped
public class LocalizedStringProducer {

    protected static String bundleName = "ApplicationMessages";

    protected static final int DEFAULT_WIDTH = 100;

    protected ResourceBundle rb;

    public LocalizedStringProducer() {
        rb = ResourceBundle.getBundle(bundleName);
    }

    protected Annotated getAnnotated(InjectionPoint ip) {
        Annotated annotated = ip.getAnnotated();
        if (annotated == null) {
            throw new RuntimeException("InjectionPoint annotated is null");
        }
        return annotated;
    }

    protected String getResourceBundleKey(Annotated annotated) {
        LocalizedStringKey messageKey = annotated.getAnnotation(LocalizedStringKey.class);
        if (messageKey == null) {
            throw new RuntimeException("InjectionPoint has no @LocalizedStringKey annotation");
        }
        return messageKey.value();
    }

    protected Map<String, String> getResourceBundleKeyMap(Annotated annotated) {
        LocalizedStringBuilder builder = annotated.getAnnotation(LocalizedStringBuilder.class);
        if (builder == null) {
            throw new RuntimeException("InjectionPoint has no @LocalizedStringBuilder annotation");
        }

        Map<String, String> map
                = new HashMap<>();

        for (LocalizedStringKey key : builder.value()) {
            map.put(key.buildId(), key.value());
        }

        return map;
    }

    private String getResourceBundleValue(String key) {
        String val = "";
        try {
            val = rb.getString(key);
        } catch (MissingResourceException e) {
            val = String.format("<missing \"%s\">", key);
        }
        return val;
    }

    @Produces
    public LocalizedString getLocalizedString(InjectionPoint ip) {
        return new LocalizedString(
                getResourceBundleValue(
                        getResourceBundleKey(getAnnotated(ip))
                )
        );
    }

    @Produces
    public LocalizedStringList getLocalizedStringList(InjectionPoint ip) {
        String rbKey
                = getResourceBundleKey(getAnnotated(ip));

        LocalizedStringList lsl = new LocalizedStringList();
        for (int i = 1; true; i++) {
            try {
                String key = String.format("%s.%d", rbKey, i);
                lsl.add(rb.getString(key));
            } catch (MissingResourceException e) {
                break;
            }
        }
        return lsl;
    }

    @Produces
    @Welcome
    public LocalizedString buildWelcomeLocalizedString(InjectionPoint ip) {
        Map<String, String> map
                = getResourceBundleKeyMap(getAnnotated(ip));

        LocalizedString m = new LocalizedString(getResourceBundleValue(map.get("message")));
        LocalizedString w = new LocalizedString(getResourceBundleValue(map.get("width")));
        LocalizedString b = new LocalizedString(getResourceBundleValue(map.get("bullet")));

        int messageWidth = m.length();
        int width = (w.intValue(DEFAULT_WIDTH) < messageWidth) ? messageWidth + 4 : w.intValue(DEFAULT_WIDTH);

        StringBuilder sp = new StringBuilder();
        {
            // top bullets
            sp.append(String.format("%s\n", StringUtils.leftPad("", width, b.toString())));
            // top side bullets
            sp.append(String.format("%s%" + (width - 1) + "s\n", b.toString(), b.toString()));
            // message
            sp.append(String.format(
                    "%s %s%" + (width - messageWidth - 2) + "s\n", b.toString(), m.toString(), b.toString()
            ));
            // bottom side bullets
            sp.append(String.format("%s%" + (width - 1) + "s\n", b.toString(), b.toString()));
            // bottom bullets
            sp.append(String.format("%s", StringUtils.leftPad("", width, b.toString())));
        }

        return new LocalizedString(sp.toString());
    }
}
