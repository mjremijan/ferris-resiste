package org.ferris.resiste.console.email;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailErrorEvent {

    public static final int ERROR_MAP = 100;
    public static final int ERROR_VIEW = 200;
    public static final int ERROR_SEND = 300;

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[EmailErrorEvent ", "]");
        sj.add(String.format("errors:%s", (errors == null) ? "null" : errors.size()));
        sj.add(String.format("draft:%s", (draft == null) ? "null" : draft));
        return sj.toString();
    }

    protected List<String> errors;
    protected Optional<EmailDraft> draft;

    public EmailErrorEvent(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public Optional<EmailDraft> getDraft() {
        return draft;
    }

    public void setDraft(Optional<EmailDraft> draft) {
        this.draft = draft;
    }
}
