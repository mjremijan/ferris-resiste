package org.ferris.resiste.console.rss;

import java.util.List;
import java.util.Optional;
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

    @Test
    public void testMarsRemote() throws Exception {
        RssUrl rssUrl
            = new RssUrl("junitid", "https://mars.nasa.gov/rss/api/?feed=news&category=all&feedtype=rss", Optional.empty());

        RssFeed rssFeed
            = rssFeedFactory.build(rssUrl);

        Assert.assertEquals("junitid", rssFeed.getId());
        Assert.assertEquals("Latest News - NASA's Mars Exploration Program", rssFeed.getTitle());
    }
    
    @Test
    public void testMarsLocal() throws Exception {
        
        RssUrl rssUrl
            = new RssUrl("junitid", "classpath://mars.nasa.gov.xml", Optional.empty());

        RssFeed rssFeed
            = rssFeedFactory.build(rssUrl);

        Assert.assertEquals("junitid", rssFeed.getId());
        Assert.assertEquals("Latest News - NASA's Mars Exploration Program", rssFeed.getTitle());
        List<RssEntry> entries = rssFeed.getEntries();
        Assert.assertEquals("https://mars.nasa.gov/news/9549/", entries.get(0).getLink());
        Assert.assertEquals("https://mars.nasa.gov/news/9448/", entries.get(entries.size()-1).getLink());
    }
    
    @Test
    public void testArtemisRemote() throws Exception {
        RssUrl rssUrl
            = new RssUrl("junitid"
                    , "https://www.nasa.gov/missions/artemis/feed/", Optional.empty());

        RssFeed rssFeed
            = rssFeedFactory.build(rssUrl);

        Assert.assertEquals("junitid", rssFeed.getId());
        Assert.assertEquals("Artemis – NASA", rssFeed.getTitle());
    }

    @Test
    public void testChristianityTodayRemote() throws Exception {
        RssUrl rssUrl
            = new RssUrl("junitid"
                    , "https://www.christiantoday.com/rss/feed", Optional.empty());

        RssFeed rssFeed
            = rssFeedFactory.build(rssUrl);

        Assert.assertEquals("junitid", rssFeed.getId());
        Assert.assertEquals("Christian Today", rssFeed.getTitle());
    }
}
