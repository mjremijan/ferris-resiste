package org.ferris.resiste.console.email;

import javax.annotation.Priority;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import static org.ferris.resiste.console.email.EmailDraftEvent.SEND;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailSendService {

    @Inject
    protected Logger log;

    @Inject
    protected EmailSender sender;

    protected void observeSend(
        @Observes @Priority(SEND) EmailDraftEvent evnt
    ) {
        log.info(String.format("ENTER %s", evnt));

        evnt.getDrafts().forEach(d ->
                sender.send(d)
        );
    }
}
