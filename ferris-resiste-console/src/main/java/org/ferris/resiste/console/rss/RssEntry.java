package org.ferris.resiste.console.rss;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.inject.Vetoed;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class RssEntry {
    public String guid;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String contents;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date publishedDate;

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    protected List<RssImage> images = new LinkedList<>();

    public List<RssImage> getImages() {
        return images;
    }

    public void addImage(RssImage image) {
        images.add(image);
    }

    protected List<RssMediaFile> mediaFiles = new LinkedList<>();

    public List<RssMediaFile> getMediaFiles() {
        return mediaFiles;
    }

    public void addMediaFile(RssMediaFile mediaFile) {
        mediaFiles.add(mediaFile);
    }




}
