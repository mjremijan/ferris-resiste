package org.ferris.resiste.console.rss;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
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
public class RssFeedFactoryIT {

    @Mock
    Logger logMock;

    RssFeedFactory rssFeedFactory;

    @Before
    public void before() {
        rssFeedFactory = new RssFeedFactory();
        rssFeedFactory.log = logMock;
    }

//    @Test
//    public void testMarsRemote() throws Exception {
//        RssUrl rssUrl
//            = new RssUrl("junitid", "https://mars.nasa.gov/rss/api/?feed=news&category=all&feedtype=rss");
//
//        RssFeed rssFeed
//            = rssFeedFactory.build(rssUrl);
//
//        Assert.assertEquals("junitid", rssFeed.getId());
//        Assert.assertEquals("Latest News - NASA's Mars Exploration Program", rssFeed.getTitle());
//    }
    
        @Test
    public void testMarsLocal() throws Exception {
        
        RssUrl rssUrl
            = new RssUrl("junitid", "classpath:mars.nasa.gov.xml");

        RssFeed rssFeed
            = rssFeedFactory.build(rssUrl);

        Assert.assertEquals("junitid", rssFeed.getId());
        Assert.assertEquals("Latest News - NASA's Mars Exploration Program", rssFeed.getTitle());
        List<RssEntry> entries = rssFeed.getEntries();
        entries.forEach(e -> System.out.printf("id=\"%s\", link=\"%s\"\n", e.getGuid(), e.getLink()));
    }
}
