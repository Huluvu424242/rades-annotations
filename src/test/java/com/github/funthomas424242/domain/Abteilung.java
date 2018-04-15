package com.github.funthomas424242.domain;

import com.github.funthomas424242.rades.annotations.accessors.RadesAddAccessor;
import com.github.funthomas424242.rades.annotations.builder.RadesAddBuilder;

import java.util.Date;

@RadesAddBuilder
@RadesAddAccessor
public class Abteilung {

    private int id;

    protected String name;

    protected String abteilungsNr;

    protected Date gruendungstag;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

}
