package org.ferris.resiste.console.logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Produces {@link Logger} objects for injection using the CDI
 * {@link InjectionPoint} to get the name of the requesting class
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class LoggerProducer {
    @Produces
    public Logger getLogger(InjectionPoint injectionPoint) {
        Logger log = LoggerFactory.getLogger(injectionPoint.getBean().getBeanClass());
        return log;
    }
}
