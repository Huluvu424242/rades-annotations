package com.github.funthomas424242.domain

import java.util.Date;

public class PersonAccessor{
    final protected Person person;

    public PersonAccessor(final Person person){
        this.person=person;
    }

    public String getName(){
        return this.person.name;
    }

    public String getVorname(){
        return this.person.vorname;
    }

    public Date getBirthday(){
        return this.person.birthday;
    }

}