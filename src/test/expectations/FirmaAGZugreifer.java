package com.github.funthomas424242.domain;
import javax.annotation.Generated;

@Generated(value="RadesAccessorProcessor"
        , date="2018-04-06T20:36:46.750"
        , comments="com.github.funthomas424242.domain.Firma")
public class FirmaAGZugreifer {

    private Firma firma;

    public FirmaAGZugreifer( final Firma firma ){
        this.firma = firma;
    }

    public java.util.Date getGruendungstag( ) {
        return this.firma.gruendungstag;
    }

    public java.lang.String getBetriebeNr( ) {
        return this.firma.betriebeNr;
    }

    public java.lang.String getName( ) {
        return this.firma.name;
    }

}
