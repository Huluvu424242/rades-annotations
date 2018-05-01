package com.github.funthomas424242.domain;

/*-
 * #%L
 * rades-annotations
 * %%
 * Copyright (C) 2018 PIUG
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
import javax.annotation.Generated;

@Generated(value="RadesAccessorProcessor"
, date="2018-04-06T20:36:46.750"
, comments="com.github.funthomas424242.domain.Firma")
public class FirmaAGZugreifer {

    protected final Firma firma;

    public FirmaAGZugreifer( final Firma firma ){
        this.firma = firma;
    }

    public Firma toFirma(){
        return this.firma;
    }

    public java.util.Date getGruendungstag( ) {
        return this.firma.gruendungstag;
    }

    public void setId(final int id){
        this.firma.setId(id);
    }

    public java.lang.String getBetriebeNr( ) {
        return this.firma.betriebeNr;
    }

    public int getId(){
        return this.firma.getId();
    }

    public java.lang.String getName( ) {
        return this.firma.name;
    }

}
