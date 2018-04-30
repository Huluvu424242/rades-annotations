package com.github.funthomas424242.rades.annotations.builder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class RadesBuilderCompatibillityTest {

    @Test
    @DisplayName("Prüfe korrektes Package für @AddBuilder")
    @Tags({@Tag("compatibillity"), @Tag("builder")})
    public void testAddBuilderPackageName() {
        assertEquals("com.github.funthomas424242.rades.annotations.builder", AddBuilder.class.getPackage().getName());
    }

    @Test
    @DisplayName("Prüfe korrektes Package für @RadesAddBuilder")
    @Tags({@Tag("compatibillity"), @Tag("builder")})
    public void testRadesAddBuilderPackageName() {
        assertEquals("com.github.funthomas424242.rades.annotations.builder", RadesAddBuilder.class.getPackage().getName());
    }

    @Test
    @DisplayName("Prüfe korrektes Package für @RadesNoBuilder")
    @Tags({@Tag("compatibillity"), @Tag("builder")})
    public void testRadesNoBuilderPackageName() {
        assertEquals("com.github.funthomas424242.rades.annotations.builder", RadesNoBuilder.class.getPackage().getName());
    }

    @Test
    @DisplayName("Prüfe korrektes Package für @NoBuilder")
    @Tags({@Tag("compatibillity"), @Tag("builder")})
    public void testNoBuilderPackageName() {
        assertEquals("com.github.funthomas424242.rades.annotations.builder", NoBuilder.class.getPackage().getName());
    }


}
