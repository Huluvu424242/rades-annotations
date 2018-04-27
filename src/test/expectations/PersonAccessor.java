package com.github.funthomas424242.domain;
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

    public int getId(){
        return this.person.getId();
    }

    public void addFreund(final java.lang.String alias, final com.github.funthomas424242.domain.Person freund){
        this.person.addFreund(alias, freund);
    }

    public int getGroesse( ) {
        return this.person.groesse;
    }

    public java.lang.String getVorname( ) {
        return this.person.vorname;
    }

    public void setId(final int id){
        this.person.setId(id);
    }

    public void removeLieblingsfarbe(final com.github.funthomas424242.domain.Person.Farbe farbe){
        this.person.removeLieblingsfarbe(farbe);
    }

    public java.util.Map<java.lang.String,com.github.funthomas424242.domain.Person> getFreunde( ) {
        return this.person.freunde;
    }

    public java.util.Set<com.github.funthomas424242.domain.Person.Farbe> getLieblingsfarben( ) {
        return this.person.lieblingsfarben;
    }

    public int computeAge(){
        return this.person.computeAge();
    }

    public java.time.LocalDate getBirthday( ) {
        return this.person.birthday;
    }

    public java.lang.String getName( ) {
        return this.person.name;
    }

    public void addLieblingsfarbe(final com.github.funthomas424242.domain.Person.Farbe farbe){
        this.person.addLieblingsfarbe(farbe);
    }

    public String toString(){
        return this.person.toString();
    }

}
