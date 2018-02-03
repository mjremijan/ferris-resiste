package org.ferris.resiste.console.text.i18n;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({TYPE, METHOD, FIELD, PARAMETER})
public @interface LocalizedStringKey {

    String value();

    String buildId() default "";
}
