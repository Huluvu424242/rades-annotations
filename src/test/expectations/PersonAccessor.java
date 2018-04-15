package com.github.funthomas424242.domain;
import javax.annotation.Generated;

@Generated(value="RadesAccessorProcessor"
        , date="2018-04-06T20:36:46.750"
        , comments="com.github.funthomas424242.domain.Person")
public class PersonAccessor {

    private Person person;

    public PersonAccessor( final Person person ){
        this.person = person;
    }

    public int getGroesse( ) {
        return this.person.groesse;
    }

    public java.lang.String getVorname( ) {
        return this.person.vorname;
    }

    public java.util.Map<java.lang.String,com.github.funthomas424242.domain.Person> getFreunde( ) {
        return this.person.freunde;
    }

    public java.util.Set<com.github.funthomas424242.domain.Person.Farbe> getLieblingsfarben( ) {
        return this.person.lieblingsfarben;
    }

    public java.time.LocalDate getBirthday( ) {
        return this.person.birthday;
    }

    public java.lang.String getName( ) {
        return this.person.name;
    }

}
