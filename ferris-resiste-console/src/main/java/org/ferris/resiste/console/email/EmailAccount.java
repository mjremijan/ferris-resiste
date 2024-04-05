package org.ferris.resiste.console.email;

import jakarta.enterprise.inject.Vetoed;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Properties;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class is a data model object representing email account data.
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Vetoed
public class EmailAccount {

    @NotNull(message = "{EmailAccount.host.NotNull.message}")
    @Size(message = "{EmailAccount.host.Size.message}", min = 4, max = 54)
    private String host;

    @NotNull(message = "{EmailAccount.port.NotNull.message}")
    @Min(message = "{EmailAccount.port.Min.message}", value = 1)
    @Max(message = "{EmailAccount.port.Max.message}", value = 65535)
    private Integer port;

    @NotNull(message = "{EmailAccount.sslEnabled.NotNull.message}")
    private Boolean sslEnabled;

    @NotNull(message = "{EmailAccount.username.NotNull.message}")
    @Size(message = "{EmailAccount.username.Size.message}", min = 5, max = 35)
    private String username;

    @NotNull(message = "{EmailAccount.password.NotNull.message}")
    @Size(message = "{EmailAccount.password.Size.message}", min = 5, max = 35)
    private transient String password;

    @NotNull(message = "{EmailAccount.emailAddress.NotNull.message}")
    @Pattern(message = "{EmailAccount.emailAddress.Pattern.message}", regexp = ".+\\@.+\\..+")
    private String emailAddress;

    private String sendToAddress;

    public EmailAccount() {};

    protected EmailAccount init(Properties props) {
        setHost(props);
        setPort(props);
        setSslEnabled(props);
        setUsername(props);
        setPassword(props);
        setEmailAddress(props);
        setSendToAddress(props);
        return this;
    }

    private void setSendToAddress(Properties props) {
        sendToAddress = props.getProperty("sendToAddress", props.getProperty("emailAddress", null));
    }

    public String getSendToAddress() {
        return sendToAddress;
    }

    private void setEmailAddress(Properties props) {
        emailAddress = props.getProperty("emailAddress", null);
    }

    private void setPassword(Properties props) {
        password = props.getProperty("password", null);
    }

    private void setUsername(Properties props) {
        username = props.getProperty("username", null);
    }

    private void setSslEnabled(Properties props) {
        String boolStr = props.getProperty("sslEnabled", null);
        if (boolStr != null) {
            if ("true".equalsIgnoreCase(boolStr)) {
                sslEnabled = Boolean.TRUE;
            } else if ("false".equalsIgnoreCase(boolStr)) {
                sslEnabled = Boolean.FALSE;
            } else {
                sslEnabled = null;
            }
        }
    }

    private void setPort(Properties props) {
        String portStr = props.getProperty("port", null);
        if (portStr != null) {
            try {
                port = new Integer(portStr);
            } catch (NumberFormatException ignore) {
                port = null;
            }
        }
    }

    private void setHost(Properties props) {
        this.host = props.getProperty("host", null);
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public Boolean isSslEnabled() {
        return sslEnabled;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringBuilder.getDefaultStyle(), false);
    }
}
