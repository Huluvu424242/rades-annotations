package com.github.funthomas424242.domain;

public class PersonBuilder {

    private Person person = new Person();

    public Person build() {
        final Person value = this.person;
        this.person = null;
        return value;
    }

    public PersonBuilder withVorname( final String vorname ) {
        this.person.vorname = vorname;
        return this;
    }

    public PersonBuilder withName( final String name ) {
        this.person.name = name;
        return this;
    }

}
