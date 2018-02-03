package org.ferris.resiste.console.email;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailSendEvent {

    private List<Status> tweetsToEmail;

    private String message;

    private String subject;


    public EmailSendEvent(List<Status> tweetsToEmail) {
        this.tweetsToEmail = tweetsToEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<Status> getTweets() {
        return Collections.unmodifiableList(tweetsToEmail);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
