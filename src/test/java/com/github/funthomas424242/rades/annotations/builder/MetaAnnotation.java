package com.github.funthomas424242.rades.annotations.builder;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@MetaMetaAnnotation
@RadesBuilder
@Retention(RetentionPolicy.SOURCE)
public @interface MetaAnnotation {
}
