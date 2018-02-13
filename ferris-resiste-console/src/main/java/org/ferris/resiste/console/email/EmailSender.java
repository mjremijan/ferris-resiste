package org.ferris.resiste.console.email;

import java.util.Properties;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.ferris.resiste.console.retry.ExceptionRetry;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
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

            Session smtp = null;
            EmailAccount emailAccount = emailAccountService.getEmailAccount();
            {
                Properties props = new Properties();
                props = new Properties();
                props.setProperty("mail.smtp.host", emailAccount.getHost());
                if (emailAccount.isSslEnabled()) {
                    props.setProperty("mail.smtp.socketFactory.port", emailAccount.getPort().toString());
                    props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                } else {
                    props.setProperty("mail.smtp.socketFactory.port", emailAccount.getPort().toString());
                    props.setProperty("mail.smtp.socketFactory.class", "javax.net.SocketFactory");
                }
                props.setProperty("mail.smtp.auth", "true");
                props.setProperty("mail.smtp.port", emailAccount.getPort().toString());

                smtp = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailAccount.getUsername(), emailAccount.getPassword());
                    }
                });
            }
            smtp.setDebug(true);
            //smtp.setDebugOut(getPrintStream());

            MimeMessage m = new MimeMessage(smtp);
            m.setContent(content);
            m.setSubject(draft.getSubject());

            m.setRecipient(Message.RecipientType.TO, new InternetAddress(emailAccount.getEmailAddress()));
            m.setFrom(new InternetAddress(emailAccount.getEmailAddress()));
            m.setReplyTo(new InternetAddress[] {new InternetAddress(emailAccount.getEmailAddress())});

            log.info(String.format("Attempt email with %s", emailAccount.toString()));
            Transport.send(m);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    protected PrintStream getPrintStream() {
//        Enumeration enu = log.getAllAppenders();
//        while (enu.hasMoreElements()) {
//            Object o = enu.nextElement();
//            if (o instanceof Log4jRollingFileAppender) {
//                return ((Log4jRollingFileAppender)o).getPrintStream();
//            }
//        }
//        return System.out;
//    }
}
