package org.ferris.resiste.console.rome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;
import org.ferris.resiste.console.rss.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@RunWith(MockitoJUnitRunner.class)
public class SyndFilterServiceTest {

    @Mock
    Logger logMock;
        
    
    
    @Test
    public void observeFilterByRegEx1() {
        // Setup
        RssUrlService rusMock = mock(RssUrlService.class);
        {
            when(rusMock.findAll()).thenReturn(
                Arrays.asList(
                    new RssUrl(
                          "somerssid"
                        , "https://some/rss.xml"
                        , Optional.of(Pattern.compile(".*Kellyoke.*"))
                    ) 
                )
            );
        }
        
        SyndFilterService sfs = new SyndFilterService();
        {
            sfs.log = logMock;
            sfs.service = rusMock;
        }
        
        SyndFilterEvent evnt = new SyndFilterEvent(
            Arrays.asList(
                new RssFeed() {{
                    setId("somerssid");
                    setEntries(
                        new ArrayList() {{
                            addAll(
                                Arrays.asList(
                                    new RssEntry() {{
                                        setFeedId("somerssid");
                                        setEntryId("somerssid-1");
                                        setTitle("somerssid-1 title");
                                        setContents("somerssid-1 contents");
                                    }}
                                    ,
                                    new RssEntry() {{
                                        setFeedId("somerssid");
                                        setEntryId("somerssid-2");
                                        setTitle("somerssid-2 title Kellyoke");
                                        setContents("somerssid-2 contents");
                                    }} 
                                    ,
                                    new RssEntry() {{
                                        setFeedId("somerssid");
                                        setEntryId("somerssid-3");
                                        setTitle("somerssid-3 title");
                                        setContents("somerssid-3 Kellyoke contents");
                                    }} 
                                )
                            );
                        }}
                  );
                }}
            )
        );
        
        // verify
        Assert.assertEquals(
              3
            , evnt.getFeeds().get(0).getEntries().size()
        );
        
        // test
        sfs.observeFilterByRegEx(evnt);
        
        // verify
        Assert.assertEquals(
              2
            , evnt.getFeeds().get(0).getEntries().size()
        );
    }
    
    @Test
    public void observeFilterByRegEx2() {
        // Setup
        RssUrlService rusMock = mock(RssUrlService.class);
        {
            when(rusMock.findAll()).thenReturn(
                Arrays.asList(
                    new RssUrl(
                          "somerssid"
                        , "https://some/rss.xml"
                        , Optional.of(Pattern.compile("Kellyoke"))
                    ) 
                )
            );
        }
        
        SyndFilterService sfs = new SyndFilterService();
        {
            sfs.log = logMock;
            sfs.service = rusMock;
        }
        
        SyndFilterEvent evnt = new SyndFilterEvent(
            Arrays.asList(
                new RssFeed() {{
                    setId("somerssid");
                    setEntries(
                        new ArrayList() {{
                            addAll(
                                Arrays.asList(
                                    new RssEntry() {{
                                        setFeedId("somerssid");
                                        setEntryId("somerssid-1");
                                        setTitle("somerssid-1 title");
                                        setContents("somerssid-1 contents");
                                    }}
                                    ,
                                    new RssEntry() {{
                                        setFeedId("somerssid");
                                        setEntryId("somerssid-2");
                                        setTitle("somerssid-2 title Kellyoke");
                                        setContents("somerssid-2 contents");
                                    }} 
                                    ,
                                    new RssEntry() {{
                                        setFeedId("somerssid");
                                        setEntryId("somerssid-3");
                                        setTitle("somerssid-3 title");
                                        setContents("somerssid-3 Kellyoke contents");
                                    }} 
                                )
                            );
                        }}
                  );
                }}
            )
        );
        
        // verify
        Assert.assertEquals(
              3
            , evnt.getFeeds().get(0).getEntries().size()
        );
        
        // test
        sfs.observeFilterByRegEx(evnt);
        
        // verify
        Assert.assertEquals(
              2
            , evnt.getFeeds().get(0).getEntries().size()
        );
    }
       
}
