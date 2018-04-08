package com.github.funthomas424242.domain;

import com.github.funthomas424242.rades.annotations.RadesAddBuilder;

@RadesAddBuilder(simpleBuilderClassName = "CarBuilder")
public class Auto {

    Object motor;

    protected String typ;

    protected String hersteller;

}
