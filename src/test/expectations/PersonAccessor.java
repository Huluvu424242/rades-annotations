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
, comments="com.github.funthomas424242.domain.Person")
public class PersonAccessor {
    protected final Person person;
    public PersonAccessor( final Person person ){
        this.person = person;
    }
    public Person toPerson(){
        return this.person;
    }
    public java.util.stream.Stream<com.github.funthomas424242.domain.Person.Farbe> getLieblingsfarben(){
        return this.person.getLieblingsfarben();
    }
    public int getId(){
        return this.person.getId();
    }
    public int getGroesse( ) {
        return this.person.groesse;
    }
    public void addFreund(final java.lang.String alias, final com.github.funthomas424242.domain.Person freund){
        this.person.addFreund(alias, freund);
    }
    public void setId(final int id){
        this.person.setId(id);
    }

    public java.lang.String toString(){
        return this.person.toString();
    }

    public int computeAge(){
        return this.person.computeAge();
    }
    public void removeLieblingsfarbe(final com.github.funthomas424242.domain.Person.Farbe farbe){
        this.person.removeLieblingsfarbe(farbe);
    }
    public void addLieblingsfarbe(final com.github.funthomas424242.domain.Person.Farbe farbe){
        this.person.addLieblingsfarbe(farbe);
    }
    public java.lang.String getVorname( ) {
        return this.person.vorname;
    }
    public java.time.LocalDate getBirthday( ) {
        return this.person.birthday;
    }
    public java.util.Map<java.lang.String,com.github.funthomas424242.domain.Person> getFreunde( ) {
        return this.person.freunde;
    }
    public java.lang.String getName( ) {
        return this.person.name;
    }
}
