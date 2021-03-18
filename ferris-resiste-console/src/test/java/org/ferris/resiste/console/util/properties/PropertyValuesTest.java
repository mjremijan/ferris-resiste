package org.ferris.resiste.console.util.properties;

import java.io.File;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ferris.resiste.console.encryption.Rsa;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class PropertyValuesTest {

    protected File path;

    @Before
    public void setUp() throws Exception {
        path = new File("src/test/resources/org/ferris/resiste/console/util/properties");
    }

    @Test
    public void test_echo() {
        // Setup
        File f = new File(path, "echo.properties");
        PropertyValues pv = new PropertyValues(f);

        // Assert
        Assert.assertEquals("mike", pv.getProperty("name"));
        Assert.assertEquals("blue", pv.getProperty("color"));
    }

    @Test
    public void test_rsa() {
        // Setup
        File f = new File(path, "rsa.properties");

        Rsa rsaMock = Mockito.mock(Rsa.class);
        Mockito.when(rsaMock.base64DecodeAndDecrypt("aabbccddeeff")).thenReturn("-username");
        Mockito.when(rsaMock.base64DecodeAndDecrypt("gghhiijjkkll")).thenReturn("+password");

        PropertyValues pv = new PropertyValues(
            f, new PropertyValueDecoderForRsa(rsaMock)
        );

        // Assert
        Assert.assertEquals("-username", pv.getProperty("username"));
        Assert.assertEquals("+password", pv.getProperty("password"));
    }

    @Test
    public void test_echo_and_rsa() {
        // Setup
        File f = new File(path, "echo_and_rsa.properties");

        Rsa rsaMock = Mockito.mock(Rsa.class);
        Mockito.when(rsaMock.base64DecodeAndDecrypt("aabbccddeeff")).thenReturn("-username");
        Mockito.when(rsaMock.base64DecodeAndDecrypt("gghhiijjkkll")).thenReturn("+password");

        PropertyValueDecoderForRsa rsaDecoder
            = new PropertyValueDecoderForRsa(rsaMock);
        rsaDecoder.next(new PropertyValueDecoderForEcho());

        PropertyValues pv = new PropertyValues(
            f, rsaDecoder
        );

        // Assert
        Assert.assertEquals("mike", pv.getProperty("name"));
        Assert.assertEquals("blue", pv.getProperty("color"));
        Assert.assertEquals("-username", pv.getProperty("username"));
        Assert.assertEquals("+password", pv.getProperty("password"));
    }
}
