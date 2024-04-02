package org.ferris.resiste.console.rss;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class RssUrlTest {

    @Test
    public void test_patternMatch() {
        
        // Setup
        RssUrl url = new RssUrl(
            "id", "http://foo"
            , Optional.of(Pattern.compile("World"))
        );
        
        // Test
        Pattern p = url.getPattern().get();
        Matcher m = p.matcher("World");
        
        Assert.assertEquals(
            true, m.find()
        );
        
        m = p.matcher("World News");
        Assert.assertEquals(
            true, m.find()
        );
        
        m = p.matcher("Hello World");
        Assert.assertEquals(
            true, m.find()
        );
    }
}
