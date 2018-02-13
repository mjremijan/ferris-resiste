package org.ferris.resiste.console.email;

import java.util.StringJoiner;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailDraft {

    protected String subject;
    protected String body;

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[EmailDraft ", "]");
        sj.add(String.format("subject:%s", (subject == null) ? "null" : subject));
        sj.add(String.format("body:%s", (body == null) ? "null" : body.length()));
        return sj.toString();
    }

    public EmailDraft(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}
