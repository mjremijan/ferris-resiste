package org.ferris.resiste.console.rss;

import java.util.Optional;
import java.util.regex.Pattern;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@RunWith(MockitoJUnitRunner.class)
public class RssUrlFactoryTest {

    @Mock
    Logger logMock;
        
    @Test
    public void test_id_and_url() {
        
        // Setup
        RssUrlFactory factory;
        {
           factory = new RssUrlFactory();
           factory.log = logMock;
        }        
        String id = "junit1";
        String url = "https://www.yahoo.com";
        RssUrl expected = null;
        {
            expected = new RssUrl(id, url, Optional.empty());
        }

        // Test
        RssUrl actual
            = factory.parse(String.format("%s,%s",id,url)).get();
       
        Assert.assertEquals(expected.toString(), actual.toString());
    }
    
    @Test
    public void test_id_and_pattern_without_comma() {
        
        // Setup
        RssUrlFactory factory;
        {
           factory = new RssUrlFactory();
           factory.log = logMock;
        }        
        String id = "junit1";
        String url = "https://www.yahoo.com";
        Pattern pattern = Pattern.compile("kellyoke");
        RssUrl expected = null;
        {
            expected = new RssUrl(id, url, Optional.of(pattern));
        }

        // Test
        RssUrl actual
            = factory.parse(String.format("%s,%s,%s",id,url,pattern.pattern())).get();
       
        Assert.assertEquals(expected.toString(), actual.toString());
    }
    
    @Test
    public void test_id_and_pattern_with_comma() {
        
        // Setup
        RssUrlFactory factory;
        {
           factory = new RssUrlFactory();
           factory.log = logMock;
        }        
        String id = "junit1";
        String url = "https://www.yahoo.com";
        Pattern pattern = Pattern.compile("kellyoke, and mikeyoke");
        RssUrl expected = null;
        {
            expected = new RssUrl(id, url, Optional.of(pattern));
        }

        // Test
        RssUrl actual
            = factory.parse(String.format("%s,%s,%s",id,url,pattern.pattern())).get();
       
        Assert.assertEquals(expected.toString(), actual.toString());
    }
       
}
