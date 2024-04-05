package org.ferris.resiste.console.rss;

import java.time.LocalDate;
import java.time.Month;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@RunWith(MockitoJUnitRunner.class)
public class RssHistoryRepositoryTest {

    @Mock
    Logger logMock;
        
    
    
    @Test
    public void test_localdate_in_the_past() {
        LocalDate ld = LocalDate.of(2024, Month.MARCH, 23);
        Assert.assertEquals("2024-03-23", ld.toString());
        ld = ld.plusMonths(-6);
        Assert.assertEquals("2023-09-23", ld.toString());
    }
       
}
