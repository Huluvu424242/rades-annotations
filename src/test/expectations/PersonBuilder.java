package com.github.funthomas424242.domain;

public class PersonBuilder {

    private Person person = new Person();

    public Person build() {
        final Person value = this.person;
        this.person = null;
        return value;
    }

    public PersonBuilder withVorname( final java.lang.String vorname ) {
        this.person.vorname = vorname;
        return this;
    }

    public PersonBuilder withBirthday( final java.util.Date birthday ) {
        this.person.birthday = birthday;
        return this;
    }

    public PersonBuilder withName( final java.lang.String name ) {
        this.person.name = name;
        return this;
    }

}
