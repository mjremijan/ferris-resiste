package org.ferris.resiste.console.rss;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class RssEntryTest {

    @Test
    public void test_setContents_removes_hieght_and_width() {
        String html 
            = "<p><a href=\"https://mars.nasa.gov/news/9549/\">\n" +
              "<img src=\"https://mars.nasa.gov/system/news_items/main_images/9549_PIA24925_MAIN.jpg\" \n   style=\"padding-right:10px; \n padding-bottom:5px;\" \n align=\"left\" \n alt=\"Read article: Team Assessing SHERLOC Instrument on NASA's Perseverance Rover\" \n width=\"100\" \n height=\"75\" \n border=\"0\" /></a><br /><img src='' height='5' foo='bar' width=9></img><br />Engineers are working to stabilize a dust cover on one of the science instrument’s cameras.</p><img src=\"f\"   width=''/><br clear=\"all\"/><img src='b' height=5></img><br /><img <";

        String expected 
            = "<p><a href=\"https://mars.nasa.gov/news/9549/\">\n" +
              "<img src=\"https://mars.nasa.gov/system/news_items/main_images/9549_PIA24925_MAIN.jpg\" \n   style=\"padding-right:10px; \n padding-bottom:5px;\" \n align=\"left\" \n alt=\"Read article: Team Assessing SHERLOC Instrument on NASA's Perseverance Rover\" \n resistewidth=\"100\" \n resisteheight=\"75\" \n border=\"0\" /></a><br /><img src='' resisteheight='5' foo='bar' resistewidth=9></img><br />Engineers are working to stabilize a dust cover on one of the science instrument’s cameras.</p><img src=\"f\"   resistewidth=''/><br clear=\"all\"/><img src='b' resisteheight=5></img><br /><img <";
        
        RssEntry entry = new RssEntry();
        entry.setContents(html);
        Assert.assertEquals(expected, entry.getContents());
    }

    @Test
    public void test_setContents_no_html() {
        String html, expected;
        html = expected =
            "plain text content with no html";

        RssEntry entry = new RssEntry();
        entry.setContents(html);
        Assert.assertEquals(expected, entry.getContents());
    }
    
    @Test
    public void test_setContents_lots_of_lessthan_but_no_tags() {
        String html, expected;
        html = expected =
            "this < is < some<thin< that migh> be <<< difficult> to parse<?> ><?";

        RssEntry entry = new RssEntry();
        entry.setContents(html);
        Assert.assertEquals(expected, entry.getContents());
    }
    
    @Test
    public void test_setContents_lots_of_img_tags_but_no_attributes() {
        String html, expected;
        html = expected =
            "this <img> tag and <img/> and <img /> and <img></img>";

        RssEntry entry = new RssEntry();
        entry.setContents(html);
        Assert.assertEquals(expected, entry.getContents());
    }
    
    @Test
    public void test_setContents_likes_to_trim_whitespace() {
        String html
            = "  this <img> tag and <img/> \n and \n <img /> and <img></img>  \n\n  ";
        
        String expected 
            = "this <img> tag and <img/> \n and \n <img /> and <img></img>";

        RssEntry entry = new RssEntry();
        entry.setContents(html);
        Assert.assertEquals(expected, entry.getContents());
    }
    
}
