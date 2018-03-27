package com.github.funthomas424242.domain;

import com.github.funthomas424242.rades.annotations.RadesBuilder;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;


@RadesBuilder
public class Person {

    public enum Farbe {
        ROT,
        GELB,
        GRÜN,
        BLAU,
        LILA,
        SCHWARZ,
        BRAUN,
        WEISS
    }

    private int id;

    @NotNull
    protected String name;

    protected String vorname;

    protected Date birthday;

    public int groesse;

    protected Set<Farbe> lieblingsfarben;


    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

}
