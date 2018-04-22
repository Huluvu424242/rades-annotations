package com.github.funthomas424242.rades.annotations.accessors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RadesAccessorCompatibillityTests {

    @Test
    @DisplayName("Prüfe korrektes Package für @AddAccessor")
    @Tags({@Tag("compatibillity"), @Tag("accessor")})
    public void testAddAccesoorPackageName() {
        assertEquals("com.github.funthomas424242.rades.annotations.accessor", AddAccessor.class.getPackage().getName());
    }

    @Test
    @DisplayName("Prüfe korrektes Package für @RadesAddAccessor")
    @Tags({@Tag("compatibillity"), @Tag("accessor")})
    public void testRadesAddAccessorPackageName() {
        assertEquals("com.github.funthomas424242.rades.annotations.accessor", RadesAddAccessor.class.getPackage().getName());
    }

    @Test
    @DisplayName("Prüfe korrektes Package für @RadesNoAccessor")
    @Tags({@Tag("compatibillity"), @Tag("accessor")})
    public void testRadesNoAccessorPackageName() {
        assertEquals("com.github.funthomas424242.rades.annotations.accessor", RadesNoAccessor.class.getPackage().getName());
    }

    @Test
    @DisplayName("Prüfe korrektes Package für @NoAccessor")
    @Tags({@Tag("compatibillity"), @Tag("accessor")})
    public void testNoAccessorPackageName() {
        assertEquals("com.github.funthomas424242.rades.annotations.accessor", NoAccessor.class.getPackage().getName());
    }

}
