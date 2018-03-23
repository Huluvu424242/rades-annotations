package com.github.funthomas424242.rades.annotations.processors;

import com.github.funthomas424242.domain.Person;
import com.github.funthomas424242.domain.PersonBuilder;
import com.github.funthomas424242.rades.annotations.RadesBuilder;
import org.junit.Test;

import static org.junit.Assert.*;


public class RadesBuilderProcessorTest {

    @Test
    public void testBuilderMethods(){
        final Person person = new PersonBuilder()
                .setName("Nachname")
                .build();
        assertNotNull(person);
    }

}