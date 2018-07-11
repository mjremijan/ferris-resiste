package org.ferris.resiste.console.email;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.ferris.resiste.console.conf.ConfDirectory;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class EmailAccountPropertiesFileProducer {

    protected EmailAccountPropertiesFile emailAccountPropertiesFile;

    @Inject
    protected Logger log;

    @Inject
    protected ConfDirectory confDirectory;

    @PostConstruct
    public void postConstruct() {
        log.info("Constructing the EmailAccountPropertiesFile");
        emailAccountPropertiesFile = new EmailAccountPropertiesFile(confDirectory);
    }

    @Produces
    public EmailAccountPropertiesFile produceEmailAccountPropertiesFile() {
        return emailAccountPropertiesFile;
    }
}
