/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
module org.ferris.resiste.console {
    requires cdi.api;
    requires javax.inject;
    requires javax.annotation.api;
    requires java.sql;
    requires org.slf4j;
    requires ch.qos.logback.core;
    requires freemarker;
    requires mail;
    requires validation.api;
    requires com.rometools.rome;
    requires org.apache.commons.lang3;    
    requires org.jdom2;
    requires javax.interceptor.api;
}
