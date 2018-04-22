package com.github.funthomas424242.rades.annotations.accessors;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Verhindert die Generierung eines Getter
 *
 * @version $Version$, $Date$
 * @since 2.2.0
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
public @interface RadesNoAccessor {

}