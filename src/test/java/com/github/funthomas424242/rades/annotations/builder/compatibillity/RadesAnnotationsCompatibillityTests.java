package com.github.funthomas424242.rades.annotations.builder.compatibillity;

import com.github.funthomas424242.rades.annotations.builder.AddBuilder;
import com.github.funthomas424242.rades.annotations.builder.RadesAddBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RadesAnnotationsCompatibillityTests {

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


}
