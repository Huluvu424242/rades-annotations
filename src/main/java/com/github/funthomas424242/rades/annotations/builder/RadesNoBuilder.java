package com.github.funthomas424242.rades.annotations.builder;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Verhindert gie Generierung einer with Methode am Builder.
 *
 * @version $Version$, $Date$
 * @since 2.2.0
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
public @interface RadesNoBuilder {

}