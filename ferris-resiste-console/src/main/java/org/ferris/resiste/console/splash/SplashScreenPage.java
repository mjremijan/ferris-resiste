package org.ferris.resiste.console.splash;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.ferris.resiste.console.application.ApplicationDirectory;
import org.ferris.resiste.console.data.DataDirectory;
import org.ferris.resiste.console.io.Console;
import org.ferris.resiste.console.main.StartupEvent;
import static org.ferris.resiste.console.main.StartupEvent.SPASH_SCREEN;
import org.ferris.resiste.console.text.i18n.LocalizedString;
import org.ferris.resiste.console.text.i18n.LocalizedStringBuilder;
import org.ferris.resiste.console.text.i18n.LocalizedStringKey;
import org.ferris.resiste.console.text.i18n.LocalizedStringList;
import org.ferris.resiste.console.text.i18n.qualifier.Welcome;
import org.ferris.resiste.console.util.version.Version;
import org.ferris.resiste.console.view.page.AbstractPage;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class SplashScreenPage extends AbstractPage {

    @Inject
    protected Logger log;

    @Inject
    protected Console console;

    @Inject
    protected ApplicationDirectory applicationDirectory;

    @Inject
    protected DataDirectory dataDirectory;

    @Inject
    protected Version version;

    @Inject
    @LocalizedStringKey("SplashScreenPage.Properties")
    protected LocalizedStringList applicationProperties;

    @Inject
    @Welcome
    @LocalizedStringBuilder({
        @LocalizedStringKey(buildId = "message", value = "SplashScreenPage.Message")
        ,
        @LocalizedStringKey(buildId = "width", value = "SplashScreenPage.Width")
        ,
        @LocalizedStringKey(buildId = "bullet", value = "SplashScreenPage.Bullet")
    })
    protected LocalizedString welcome;

    @PostConstruct
    protected void postConstruct() {
        replaceUser(applicationProperties);
        replaceJava(applicationProperties);
        replaceApplication(applicationProperties);
    }

    protected void replaceUser(LocalizedStringList properties) {
        properties.replace("t{user.name}", System.getProperty("user.name"));
        properties.replace("t{user.home}", System.getProperty("user.home"));
        properties.replace("t{user.directory}", System.getProperty("user.dir"));
    }

    protected void replaceJava(LocalizedStringList properties) {
        properties.replace("t{java.home}", System.getProperty("java.home"));
        properties.replace("t{java.vendor}", System.getProperty("java.vendor"));
        properties.replace("t{java.version}", System.getProperty("java.version"));
    }

    protected void replaceApplication(LocalizedStringList properties) {
        properties.replace("t{resiste.title}", version.getImplementationTitle());
        properties.replace("t{resiste.vender}", version.getImplementationVendor());
        properties.replace("t{resiste.version}", version.getImplementationVersion());
        properties.replace("t{resiste.directory}", applicationDirectory.getAbsolutePath());
        properties.replace("t{resiste.data}", dataDirectory.getAbsolutePath());
    }

    public void observesStartup(
        @Observes @Priority(SPASH_SCREEN) StartupEvent event
    ) {
        log.info(String.format("ENTER %s", event));

        console.p(applicationProperties);
        console.p(welcome);
    }
}
