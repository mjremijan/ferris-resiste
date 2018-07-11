package org.ferris.resiste.console.email;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.ferris.resiste.console.conf.ConfDirectory;
import static org.ferris.resiste.console.email.EmailErrorEvent.ERROR_MAP;
import org.ferris.resiste.console.util.version.Version;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class EmailErrorService {

    @Inject
    protected Logger log;

    @Inject
    protected Version version;

    @Inject
    protected ConfDirectory confDirectory;

    private Template subjectTemplate, bodyTemplate;

    @PostConstruct
    protected void postConstruct() {
        try {
            // -------------------- Create a configuration instance
            // Create your Configuration instance, and specify if up to what FreeMarker
            // version (here 2.3.25) do you want to apply the fixes that are not 100%
            // backward-compatible. See the Configuration JavaDoc for details.
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);

            // Specify the source where the template files come from. Here I set a
            // plain directory for it, but non-file-system sources are possible too:
            cfg.setDirectoryForTemplateLoading(confDirectory);

            // Set the preferred charset template files are stored in. UTF-8 is
            // a good choice in most applications:
            cfg.setDefaultEncoding("UTF-8");

            // Sets how errors will appear.
            // During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

            // Don't log exceptions inside FreeMarker that it will thrown at you anyway:
            cfg.setLogTemplateExceptions(false);

            // Subject template
            subjectTemplate = cfg.getTemplate("error_email_subject.ftlt");

            // Body template
            bodyTemplate = cfg.getTemplate("error_email_body.ftlt");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    protected void observeSend(
        @Observes @Priority(ERROR_MAP) EmailErrorEvent evnt
    ) {
        log.info(String.format("ENTER %s", evnt));

        Optional<EmailDraft> draft
            = Optional.empty();

        if (!evnt.getErrors().isEmpty()) {
            draft = Optional.of(new EmailDraft(
                  renderSubject()
                , renderBody(evnt.getErrors())
            ));
        }

        evnt.setDraft(draft);
    }

    private String renderBody(List<String> errors) {
        log.debug("ENTER");

        // Project final name
        String projectFinalName
            = String.format("%s-%s", version.getImplementationTitle(), version.getImplementationVersion());

        // Project url
        String projectUrl
            = version.getImplementationUrl();

        // Render
        Writer out = new StringWriter();
        try {
            Map<String, Object> root = new HashMap<>();
            root.put("errors", errors);
            root.put("projectFinalName", projectFinalName);
            root.put("projectUrl", projectUrl);

            bodyTemplate.process(root, out);

            out.flush();
        } catch (Exception ex) {
            throw new RuntimeException(
                String.format("Problem rendering the email body")
                , ex
            );
        }
        log.debug(String.format("RENERED = %n%s", out.toString()));

        return out.toString();
    }


    private String renderSubject() {
        log.debug("ENTER");

        // Render
        Writer out = new StringWriter();
        try {
            subjectTemplate.process(new HashMap<>(), out);
            out.flush();
        } catch (Exception ex) {
            throw new RuntimeException(
                String.format("Problem rendering the email subject")
                , ex
            );
        }
        log.debug(String.format("RENERED = \"%s\"", out.toString()));

        return out.toString();
    }
}
