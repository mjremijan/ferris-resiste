package org.ferris.resiste.console.rss;

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
              "<img style=\"width:99%; max-width:99%;\"  src=\"https://mars.nasa.gov/system/news_items/main_images/9549_PIA24925_MAIN.jpg\" \n   style=\"padding-right:10px; \n padding-bottom:5px;\" \n align=\"left\" \n alt=\"Read article: Team Assessing SHERLOC Instrument on NASA's Perseverance Rover\" \n resistewidth=\"100\" \n resisteheight=\"75\" \n border=\"0\" /></a><br /><img style=\"width:99%; max-width:99%;\"  src='' resisteheight='5' foo='bar' resistewidth=9></img><br />Engineers are working to stabilize a dust cover on one of the science instrument’s cameras.</p><img style=\"width:99%; max-width:99%;\"  src=\"f\"   resistewidth=''/><br clear=\"all\"/><img style=\"width:99%; max-width:99%;\"  src='b' resisteheight=5></img><br /><img style=\"width:99%; max-width:99%;\"  <";
        
        RssEntry entry = new RssEntry(); 
        
        entry.setContents(html);
        System.out.printf("*1* %s%n", expected);
        System.out.printf("*2* %s%n", entry.getContents());
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
        String html = 
            "this <img> tag and <img/> and <img /> and <img></img>";
        String expected =
            "this <img style=\"width:99%; max-width:99%;\" > tag and <img style=\"width:99%; max-width:99%;\" /> and <img style=\"width:99%; max-width:99%;\"  /> and <img style=\"width:99%; max-width:99%;\" ></img>";   
        
        RssEntry entry = new RssEntry();
        entry.setContents(html);
        System.out.printf("*A* %s%n", expected);
        System.out.printf("*B* %s%n", entry.getContents());
        Assert.assertEquals(expected, entry.getContents());
    }
    
    @Test
    public void test_setContents_likes_to_trim_whitespace() {
        String html
            = "  this <img> tag and <img/> \n and \n <img /> and <img></img>  \n\n  ";
        
        String expected 
            = "this <img style=\"width:99%; max-width:99%;\"  style=\"width:99%; max-width:99%;\" > tag and <img style=\"width:99%; max-width:99%;\" /> \n and \n <img style=\"width:99%; max-width:99%;\"  /> and <img style=\"width:99%; max-width:99%;\" ></img>";

        RssEntry entry = new RssEntry();
        entry.setContents(html);
        
    }
    
     @Test
     public void testReplaceAllLowerCase() {
        String html
            = "  this <img> tag and <img/> \n and \n <img /> and <img></img>  \n\n  ";
        
        String expected
            = "  this <img style=\"width:100%; object-fit:cover;\" > tag and <img style=\"width:100%; object-fit:cover;\" /> \n and \n <img style=\"width:100%; object-fit:cover;\"  /> and <img style=\"width:100%; object-fit:cover;\" ></img>  \n\n  ";
        
        String replaceAll
            = html.replaceAll("<img", "<img style=\"width:100%; object-fit:cover;\" ");
        
        Assert.assertEquals(expected, replaceAll);  
     }
     
     @Test
     public void testReplaceAllMixedCase() {
        String html
            = "  this <IMG> tag "
                + "and <Img/> \n "
                + "and \n <iMg /> "
                + "and <imG></img>  \n\n  ";
        
        String expected
            = "  this <IMG style=\"width:100%; object-fit:cover;\" > tag "
                + "and <Img style=\"width:100%; object-fit:cover;\" /> \n "
                + "and \n <iMg style=\"width:100%; object-fit:cover;\"  /> "
                + "and <imG style=\"width:100%; object-fit:cover;\" ></img>  \n\n  ";
        
        String replaceAll
            = html.replaceAll("<([iI])([mM])([gG])", "<$1$2$3 style=\"width:100%; object-fit:cover;\" ");
        
        Assert.assertEquals(expected, replaceAll);  
     }
    
}
