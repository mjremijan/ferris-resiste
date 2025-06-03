package org.ferris.resiste.console.util;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Michael
 */
public class FilenameUtilsTest {

    public static void main(String[] args) {
        new FilenameUtilsTest().testRemovingExtension();
    }
    
    @Test
    void testRemovingExtension() {
        String url
                = "https://cdn.mos.cms.futurecdn.net/5DUh8HXQKMsKtHTxKwXXgZ.jpg";

        String expected
                = "https://cdn.mos.cms.futurecdn.net/5DUh8HXQKMsKtHTxKwXXgZ";

        String actual
                = url.substring(0, url.lastIndexOf("."));

        Assert.assertEquals(expected, actual);

    }
}
