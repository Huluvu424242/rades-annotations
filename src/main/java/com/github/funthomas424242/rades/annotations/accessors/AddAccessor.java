package com.github.funthomas424242.rades.annotations.accessors;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Generiert eine Accessor Klasse.
 * Die generierte Accessorklasse wird im gleichen Package erstellt.
 * Als Name der Accessorklasse wird der Name des annotierten Types + "Accessor" verwendet.
 *
 * @version $Version$, $Date$
 * @since 2.0.0
 */
@Documented
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface AddAccessor {

    String simpleAccessorClassName() default "";

}