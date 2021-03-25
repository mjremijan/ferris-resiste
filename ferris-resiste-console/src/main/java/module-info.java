/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
module FerrisResisteConsole {
    requires cdi.api;
    requires javax.inject;
    requires javax.annotation.api;
    requires java.sql;
    requires slf4j.api;
    requires logback.core;
    requires freemarker;
    requires mail;
    requires validation.api;
    requires rome;
    requires rome.utils;
    requires org.apache.commons.lang3;
    requires jdom2;
    requires javax.interceptor.api;
}
