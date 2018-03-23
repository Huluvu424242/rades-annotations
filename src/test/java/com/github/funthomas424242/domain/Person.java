package com.github.funthomas424242.domain;

import com.github.funthomas424242.rades.annotations.RadesBuilder;


public class Person {

    private int id;

    protected String name;

    @RadesBuilder
    public void setName(final String name){
        this.name = name;
    }


}
