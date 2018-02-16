package org.ferris.resiste.console.text.i18n;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class LocalizedStringTest {


    @Test
    public void testMe() {
        LocalizedString ls = new LocalizedString("foo");
        Assert.assertEquals("foo", ls.toString());
    }
}
