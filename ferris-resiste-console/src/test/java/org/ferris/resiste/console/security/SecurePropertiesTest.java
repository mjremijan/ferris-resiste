package org.ferris.resiste.console.security;

import java.io.File;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class SecurePropertiesTest {

    protected File path;

    @Before
    public void setUp() throws Exception {
        path = new File("src/test/resources/org/ferris/resiste/console/security");
    }

    @Test
    public void test_echo() {
        // Setup
        File f = new File(path, "echo.properties");
        SecureProperties pv = new SecureProperties(f, Optional.empty());

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

        SecureProperties pv = new SecureProperties(
            f, Optional.of(rsaMock)
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

        SecureProperties pv = new SecureProperties(
            f, Optional.of(rsaMock)
        );

        // Assert
        Assert.assertEquals("mike", pv.getProperty("name"));
        Assert.assertEquals("blue", pv.getProperty("color"));
        Assert.assertEquals("-username", pv.getProperty("username"));
        Assert.assertEquals("+password", pv.getProperty("password"));
    }
}
