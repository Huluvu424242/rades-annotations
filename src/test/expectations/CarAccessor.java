package com.github.funthomas424242.domain;
import javax.annotation.Generated;

@Generated(value="RadesAccessorProcessor"
, date="2018-04-06T20:36:46.750"
, comments="com.github.funthomas424242.domain.Auto")
public class CarAccessor {

    private Auto auto;

    public CarAccessor( final Auto auto ){
        this.auto = auto;
    }

    public java.lang.String getHersteller( ) {
        return this.auto.hersteller;
    }

    public java.lang.String getTyp( ) {
        return this.auto.typ;
    }

    public java.lang.String getMotor( ) { 
        return this.auto.motor;
    }

}
