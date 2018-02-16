package org.ferris.resiste.console.email;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.ferris.resiste.console.conf.ConfDirectory;
import static org.ferris.resiste.console.email.EmailDraftEvent.DRAFT_MAP;
import org.ferris.resiste.console.lang.StringUtils;
import org.ferris.resiste.console.rss.RssEntry;
import org.ferris.resiste.console.rss.RssFeed;
import org.ferris.resiste.console.text.i18n.LocalizedString;
import org.ferris.resiste.console.text.i18n.LocalizedStringKey;
import org.ferris.resiste.console.util.version.Version;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailDraftService {

    @Inject
    protected Logger log;

    @Inject
    protected Version version;

    @Inject
    @LocalizedStringKey("EmailDraft.NoTitle")
    protected LocalizedString noTitle;

    @Inject
    @LocalizedStringKey("EmailDraft.NoContents")
    protected LocalizedString noContents;

    @Inject
    @LocalizedStringKey("EmailDraft.NoAuthor")
    protected LocalizedString noAuthor;

    @Inject
    @LocalizedStringKey("EmailDraft.NoDate")
    protected LocalizedString noDate;

    @Inject
    @LocalizedStringKey("EmailDraft.NoLinkHref")
    protected LocalizedString noLinkHref;

    @Inject
    @LocalizedStringKey("EmailDraft.NoLinkText")
    protected LocalizedString noLinkText;

    @Inject
    protected ConfDirectory confDirectory;

    private Template subjectTemplate, bodyTemplate;

    @PostConstruct
    protected void postConstruct() throws Exception {
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
        subjectTemplate = cfg.getTemplate("rss_email_subject.ftlt");

        // Body template
        bodyTemplate = cfg.getTemplate("rss_email_body.ftlt");
    }


    protected void observeSend(
        @Observes @Priority(DRAFT_MAP) EmailDraftEvent evnt
    ) {
        log.info(String.format("ENTER %s", evnt));

        List<EmailDraft> drafts
            = new LinkedList<>();

        log.info(String.format("READ: Get list of SyndFeed"));
        List<RssFeed> feeds
            = evnt.getFeeds();

        log.info(String.format("PROCESS: Convert SyndFeed to EmailDraft"));
        feeds.stream().forEach(
            sf -> sf.getEntries().stream().forEach(se -> {

                EmailDraft ed = new EmailDraft(
                      renderSubject(sf)
                    , renderBody(se)
                );

                // Add to list
                drafts.add(ed);
            })
        );

        evnt.setDrafts(drafts);
    }

    protected String renderBody(RssEntry se) {
        log.info("ENTER");

        // Title
        String title = Optional.ofNullable(StringUtils.trimToNull(se.getTitle())).orElse(noTitle.toString());
        log.debug(String.format("TITLE = \"%s\"", title));

        // Contents
        String contents
            = (se.getContents().length() > 0) ? se.getContents() : noContents.toString();

        // Author
        String author = Optional.ofNullable(StringUtils.trimToNull(se.getAuthor())).orElse(noAuthor.toString());
        log.debug(String.format("AUTHOR = \"%s\"", author));

        // Published date (YYYY, MMMM dd)
        String publishedDate
            = Optional.ofNullable(se.getPublishedDate()).map(
                d -> new SimpleDateFormat("yyyy, MMMM dd").format(d)).orElse(noDate.toString());
        log.debug(String.format("PUBLISHED_DATE = \"%s\"", publishedDate));

        // Link href
        String linkHref
            = Optional.ofNullable(StringUtils.trimToNull(se.getLink())).orElse(noLinkHref.toString());
        log.debug(String.format("LINK_HREF = \"%s\"", linkHref));

        // Link text
        String linkText
            = Optional.ofNullable(StringUtils.trimToNull(se.getLink())).orElse(noLinkText.toString());
        log.debug(String.format("LINK_TEXT = \"%s\"", linkText));

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
            root.put("contents", contents);
            root.put("author", author);
            root.put("publishedDate", publishedDate);
            root.put("title", title);
            root.put("linkHref", linkHref);
            root.put("linkText", linkText);
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
        log.info(String.format("RENERED = %n%s", out.toString()));

        return out.toString();
    }


    protected String renderSubject(RssFeed sf) {
        log.info("ENTER");

        // Title
        String title = Optional.ofNullable(StringUtils.trimToNull(sf.getTitle())).orElse(noTitle.toString());
        log.debug(String.format("TITLE = \"%s\"", title));

        // Subject
        String subject = StringUtils.abbreviate(title, 45);
        log.debug(String.format("SUBJECT = \"%s\"", subject));

        // Render
        Writer out = new StringWriter();
        try {
            Map<String, Object> root = new HashMap<>();
            root.put("subject", subject);
            subjectTemplate.process(root, out);
            out.flush();
        } catch (Exception ex) {
            throw new RuntimeException(
                String.format("Problem rendering the email subject")
                , ex
            );
        }
        log.info(String.format("RENERED = \"%s\"", out.toString()));

        return out.toString();
    }
}
