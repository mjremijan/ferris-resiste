package org.ferris.resiste.console.rss;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javax.enterprise.inject.Vetoed;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class RssEntry {
    
    private static String trim(String in) {
        return (in == null) ? null : in.trim();
    }
    
    public String feedId;
    
    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = trim(feedId);
    }
    
    public String entryId;

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = trim(entryId);
    }

    public String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = trim(title);
    }

    public String contents;

    public String getContents() {
        return contents;
    }

    public void setContents(String theContents) {
        contents 
            = trim(theContents);
        
        // This code will look for <img> tags within
        // the contents of the RSS entry. The idea is
        // for this code to remove the "height" and
        // "width" attributes from the <img> tag so
        // that pictures display without being too
        // big.
        try 
        {
            List<Img> theTags
                = new LinkedList<>();

            char [] htmlArr 
                = contents.toCharArray();

            // Find all of the <img> tags and their contents
            for (int begin=0; begin<htmlArr.length; begin++) {
                if (htmlArr[begin] == '<') {
                    String tag = contents.substring(begin, Math.min(begin+4, contents.length())).toLowerCase();
                    if (tag.equalsIgnoreCase("<img")) {
                        int end = contents.indexOf('>', begin);
                        if (end != -1) {
                            String imgstr 
                                = contents.substring(begin, Math.min(end + 1, contents.length()));
                            if (imgstr != null) {
                                int h = imgstr.toLowerCase().indexOf("height");
                                int w = imgstr.toLowerCase().indexOf("width");
                                if (h != -1) {
                                    h = begin + h;
                                }
                                if (w != -1) {
                                    w = begin + w;
                                }
                                if (h != -1 || w != -1) {
                                    theTags.add(
                                        new Img(begin, end, w, h, imgstr)
                                    );
                                }
                            }
                        }
                    }
                }
            }
            
            // Sort the index values for the "height"
            // and "width" atrributes from lowest to
            // highests, removing "-1" values meaning
            // the attribute was not found
            List<Integer> sortedIndexes 
            = theTags.stream()
                .map(t -> Arrays.asList(t.heightBegin, t.widthBegin))
                .flatMap(list -> list.stream())
                .filter(i -> i != -1)
                .sorted()
                .collect(Collectors.toList())
            ;
            
            // Recreate the contents of the RSS entry, but
            // remove the "height" and "width" attributes...
            // or making them ineffective to HTML rendering
            AtomicInteger index = new AtomicInteger(0);
            List<String> split = sortedIndexes.stream()
                .map(end -> 
                        contents.substring(index.getAndSet(end), Math.min(end, contents.length()))
                        + "resiste"
                ).collect(Collectors.toList())
            ;
            split.add(contents.substring(index.get()));
            
            // Save final contents
            contents = String.join("", split);
        } catch (Exception e) {
            throw new RuntimeException(
                String.format("An problem occured trying to process the <img> "
                    + "tags of the RssEntry content. Original HTML: \"%s\"",theContents)
              , e
            );
        }
        
        // This code will add more styling to the <img>, hopefully to help Yahoo!
        this.contents
            = contents.replaceAll("<([iI])([mM])([gG])", "<$1$2$3 style=\"max-width: 99%;\"");
    }

    class Img {
        Integer tagBegin;
        Integer tagEnd;
        Integer widthBegin;
        Integer heightBegin;
        String contents;

        public Img(Integer tagBegin, Integer tagEnd, Integer widthBegin, Integer heightBegin, String contents) {
            this.tagBegin = tagBegin;
            this.tagEnd = tagEnd;
            this.widthBegin = widthBegin;
            this.heightBegin = heightBegin;
            this.contents = contents;
        }

        @Override
        public String toString() {
            StringJoiner sj = new StringJoiner(", ", "[Img ", "]");
            sj.add(String.format("tagBegin:%s", (tagBegin == null) ? "null" : tagBegin));
            sj.add(String.format("tagEnd:%s", (tagEnd == null) ? "null" : tagEnd));
            sj.add(String.format("widthBegin:%s", (widthBegin == null) ? "null" : widthBegin));
            sj.add(String.format("heightBegin:%s", (heightBegin == null) ? "null" : heightBegin));
            sj.add(String.format("contents:\"%s\"", contents));
            return sj.toString();
        }
    }
    
    public String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = trim(author);
    }

    public String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = trim(link);
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
