package org.ferris.resiste.console.email;

import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ValidatorFactory;
import org.apache.log4j.Logger;

/**
 * This class contains the business logic for handling email related data.
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class EmailService {

    @Inject
    protected Logger log;

    @Inject
    protected EmailPropertiesFile emailProperties;

    @Inject
    protected ValidatorFactory validatorFactory;

    @Inject
    protected Instance<EmailAccount> emailAccountInstance;

    /**
     * Get email account information
     *
     * @return Return an {@link EmailAccount} instance. Never returns
     * {@code null}. If no account data is found, an {@link EmailAccount}
     * instance is returned with all its properties set to null.
     */
    public EmailAccount getEmailAccount() {
        log.info("Create EmailAccount from properties");
        EmailAccount account = emailAccountInstance.get().init(
            emailProperties.toProperties()
        );

        Set<ConstraintViolation<EmailAccount>> violations
                = validatorFactory.getValidator().validate(account);

        if (!violations.isEmpty()) {
            violations.stream()
                .forEach(cv -> log.fatal(cv.getMessage()));
            throw new RuntimeException("Email account information is not valid.  See log files for details.");
        }

        return account;
    }
}
