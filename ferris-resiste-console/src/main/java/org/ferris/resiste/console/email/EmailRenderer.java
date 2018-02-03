package org.ferris.resiste.console.email;

import java.text.SimpleDateFormat;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.ferris.resiste.console.lang.StringDecorator;
import org.ferris.resiste.console.text.i18n.LocalizedString;
import org.ferris.resiste.console.text.i18n.LocalizedStringKey;
import org.jboss.weld.experimental.Priority;


/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailRenderer {

    @Inject
    protected Logger log;

    @Inject
    @LocalizedStringKey("EmailRenderer.Email")
    protected LocalizedString email;

    @Inject
    @LocalizedStringKey("EmailRenderer.Tweet")
    protected LocalizedString tweet;

    @Inject
    @LocalizedStringKey("EmailRenderer.ReTweetImage")
    protected LocalizedString reTweetImage;

    @Inject
    @LocalizedStringKey("EmailRenderer.ReTweetUser")
    protected LocalizedString reTweetUser;

    @Inject
    @LocalizedStringKey("EmailRenderer.MediaImage")
    protected LocalizedString mediaImage;

    @Inject
    @LocalizedStringKey("EmailRenderer.Subject")
    protected LocalizedString subject;

    @Inject
    @LocalizedStringKey("EmailRenderer.Quote")
    protected LocalizedString quote;

    public void render(
        @Observes @Priority(EmailSendPriority.RENDER_EMAIL_MESSAGE)
        EmailSendEvent evnt
    ) {
        if (evnt.getTweets().isEmpty()) {
            evnt.setSubject("");
            evnt.setMessage("");
            return;
        }

        evnt.setSubject(subject.toString());

        StringBuilder sp = new StringBuilder();
        for (Status status : evnt.getTweets())
        {
            StringDecorator tweet = new StringDecorator(this.tweet.toString());
            StringDecorator reTweetImage = new StringDecorator(this.reTweetImage.toString());
            StringDecorator reTweetUser = new StringDecorator(this.reTweetUser.toString());
            StringDecorator mediaImage = new StringDecorator(this.mediaImage.toString());
            StringDecorator quote = new StringDecorator(this.quote.toString());

            // retweet information
            renderRetweetImage(tweet, reTweetImage, status);
            renderRetweetUser(tweet, reTweetUser, status);

            if (status.isRetweet()) {
                status = status.getRetweetedStatus();
            }

            // tweet infromation
            renderUserProfileImageUrl(tweet, status);
            renderUserScreenName(tweet, status);
            renderStatusId(tweet, status);
            renderUserName(tweet, status);
            renderStatusCreatedAt(tweet, status);
            renderStatusText(tweet, status);
            renderQuote(tweet, status);
            renderMediaImage(tweet, mediaImage, status);
            sp.append(tweet.toStringDecorated());
            sp.append("\n");
        }
        String template = email.toString();
        template = template.replace("[include:Tweets]", sp.toString());
        evnt.setMessage(template);
    }

    protected void renderQuote(StringDecorator tweet, Status status) {

        StringBuilder replaceWith = new StringBuilder();
        if (status.getQuotedStatus() != null) {
            StringDecorator quoteTemplate = new StringDecorator(this.quote.toString());
            StringDecorator mediaImageTemplate = new StringDecorator(this.mediaImage.toString());
            Status quote = status.getQuotedStatus();

            renderUserName(quoteTemplate, quote);
            renderUserScreenName(quoteTemplate, quote);
            renderStatusText(quoteTemplate, quote);
            renderMediaImage(quoteTemplate, mediaImageTemplate, quote);

            replaceWith.append(quoteTemplate.toStringDecorated());
        }

        tweet.decorate("[include:Quote]", s->replaceWith.toString());
    }


    protected void renderRetweetUser(StringDecorator tweet, StringDecorator reTweetUser, Status status) {
        if (status.isRetweet()) {
            reTweetUser.decorate("[retweetuser.name]", s->status.getUser().getName());
            tweet.decorate("[include:ReTweetUser]", s->reTweetUser.toStringDecorated());
        } else {
            tweet.decorate("[include:ReTweetUser]", s->"");
        }
    }


    protected void renderRetweetImage(StringDecorator tweet, StringDecorator reTweetImage, Status status) {
        if (status.isRetweet()) {
            tweet.decorate("[include:ReTweetImage]", s->reTweetImage.toStringDecorated());
        } else {
            tweet.decorate("[include:ReTweetImage]", s->"");
        }
    }


    protected void renderUserProfileImageUrl(StringDecorator sr, Status status) {
        sr.decorate("[user.profileImageURL]", s->status.getUser().getProfileImageURL());
    }


    protected void renderUserScreenName(StringDecorator sr, Status status) {
        sr.decorate("[user.screenName]", s->status.getUser().getScreenName());
    }


    protected void renderStatusId(StringDecorator sr, Status status) {
        sr.decorate("[status.id]", s->String.valueOf(status.getId()));
    }


    protected void renderUserName(StringDecorator sr, Status status) {
        sr.decorate("[user.name]", s->status.getUser().getName());
    }


    protected void renderStatusCreatedAt(StringDecorator sr, Status status) {
        SimpleDateFormat sdf = new SimpleDateFormat("M/d h:mma");
        StringBuilder asStr = new StringBuilder();
        asStr.append(sdf.format(status.getCreatedAt()).toLowerCase());
        asStr.deleteCharAt(asStr.length()-1);
        sr.decorate("[status.createdAt]", s->asStr.toString());
    }


    protected void renderStatusText(StringDecorator sr, Status status) {
        StringDecorator text = new StringDecorator(status.getText());

        // Quoted status
        Long quoteId
            = (status.getQuotedStatus() == null) ? null : status.getQuotedStatus().getId();

        // URLs
        URLEntity[] urlEntities = status.getURLEntities();
        if (urlEntities != null) {
            for (URLEntity entity : urlEntities) {
                if (quoteId != null && entity.getExpandedURL().contains(quoteId.toString())) {
                    text.decorate(
                    entity.getStart(), entity.getEnd()-1
                        , (s) -> String.format("", entity.getExpandedURL())
                    );
                } else {
                    text.decorate(
                        entity.getStart(), entity.getEnd()-1
                        , (s) -> String.format("<a href=\"%1$s\">%1$s</a>", entity.getExpandedURL())
                    );
                }
            }
        }

        // Hashtags
        // https://twitter.com/search?q=%23UnlimitedScreaming
        HashtagEntity[] hashtagEntities = status.getHashtagEntities();
        if (hashtagEntities != null) {
            for (HashtagEntity entity : hashtagEntities) {
                text.decorate(
                    entity.getStart(), entity.getEnd()-1
                    , (s) -> String.format(
                          "<a href=\"https://twitter.com/search?f=tweets&vertical=default&q=%%23%1$s\">#%1$s</a>"
                        , entity.getText()
                      )
                );
            }
        }

        // User mentions
        // https://twitter.com/farmtrukstl/
        // https://twitter.com/PTXofficial/
        UserMentionEntity[] userMentionEntities = status.getUserMentionEntities();
        if (userMentionEntities != null) {
            for (UserMentionEntity entity : userMentionEntities) {
                text.decorate(
                    entity.getStart(), entity.getEnd()-1
                    , (s) -> String.format("<a href=\"https://twitter.com/%1$s\">@%1$s</a>", entity.getScreenName())
                );
            }
        }

        // Media Image
        MediaEntity[] mediaEntities = status.getMediaEntities();
        if (mediaEntities != null && mediaEntities.length >= 1) {
            MediaEntity entity = mediaEntities[0];
            if ("photo".equalsIgnoreCase(entity.getType())) {
                text.decorate(entity.getURL(), s->"");
            }
        }

        sr.decorate("[status.text]", s->text.toStringDecorated());
    }


    protected void renderMediaImage(StringDecorator tweet, StringDecorator mediaImage, Status status) {

        StringBuilder replaceWith = new StringBuilder();

        // Media entities
        MediaEntity[] mediaEntities = status.getMediaEntities();
        if (mediaEntities != null && mediaEntities.length >= 1) {
            MediaEntity entity = mediaEntities[0];
            if ("photo".equalsIgnoreCase(entity.getType())) {

                // URL of image
                if (entity.getMediaURL() != null && !entity.getMediaURL().isEmpty()) {
                    mediaImage.decorate("[media.url]", s->entity.getMediaURL());
                } else {
                    mediaImage.decorate("[media.url]", s->entity.getMediaURLHttps());
                }

                // Replace with
                replaceWith.append(mediaImage.toStringDecorated());
            }
        }

        tweet.decorate("[include:MediaImage]", s->replaceWith.toString());
    }
}
