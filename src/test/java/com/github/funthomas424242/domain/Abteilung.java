package com.github.funthomas424242.domain;

import com.github.funthomas424242.MetaAnnotation;

@MetaAnnotation
public class Abteilung {

    private int id;

    protected String name;

    protected String abteilungsNr;

//    protected Date gruendungstag;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

}
