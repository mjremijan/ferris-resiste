package org.ferris.resiste.console.email;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.util.Properties;
import org.ferris.resiste.console.retry.ExceptionRetry;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class EmailSender {

    @Inject
    protected Logger log;

    @Inject
    protected EmailAccountService emailAccountService;


    @ExceptionRetry
    public void send(EmailDraft draft)
    {
        log.info(String.format("ENTER %s", draft));

        try {
            // Create MimeMultipart
            MimeMultipart content = new MimeMultipart("related");

            // html part
            {
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setText(draft.getBody(), "UTF8", "html");
                content.addBodyPart(textPart);
            }

            // properties
            EmailAccount emailAccount = emailAccountService.getEmailAccount();
            Properties props = new Properties();
            if (emailAccount.isSslEnabled()) {
                props.setProperty("mail.smtp.auth", "true");
                props.setProperty("mail.smtp.host", emailAccount.getHost());
                props.setProperty("mail.smtp.socketFactory.port", emailAccount.getPort().toString());
                props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            } else {
                props.setProperty("mail.smtp.auth", "true");
                props.setProperty("mail.smtp.host", emailAccount.getHost());
                props.setProperty("mail.smtp.port", emailAccount.getPort().toString());
                props.setProperty("mail.smtp.starttls.enable", "true");
            }

            Session smtp = null;
            {
                smtp = Session.getInstance(props, new Authenticator() {                    
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                              emailAccount.getUsername()
                            , emailAccount.getPassword()
                        );
                    }
                });
                smtp.setDebug(false);
            }

            MimeMessage m = new MimeMessage(smtp);
            {
                // to
                m.setRecipient(
                      Message.RecipientType.TO
                    , new InternetAddress(emailAccount.getSendToAddress())
                );

                // subject
                m.setSubject(draft.getSubject());

                // from
                {
                    InternetAddress from = new InternetAddress(emailAccount.getEmailAddress());
                    from.setPersonal("Resiste");
                    m.setFrom(from);
                }

                // reply
                {
                    InternetAddress reply = new InternetAddress(emailAccount.getEmailAddress());
                    reply.setPersonal("Resiste");
                    m.setReplyTo(new InternetAddress[] {reply});
                }

                m.setContent(content);
            }

            log.info(String.format("Attempt email with %s", emailAccount.toString()));
            Transport.send(m);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
