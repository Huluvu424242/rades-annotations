package com.github.funthomas424242.domain;

import com.github.funthomas424242.rades.annotations.accessors.AddAccessor;
import com.github.funthomas424242.rades.annotations.builder.AddBuilder;

import java.util.Date;

@AddBuilder(simpleBuilderClassName = "FirmaAGErbauer")
@AddAccessor(simpleAccessorClassName =  "FirmaAGZugreifer")
public class Firma {

    private int id;

    protected String name;

    protected String betriebeNr;

    protected Date gruendungstag;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

}
