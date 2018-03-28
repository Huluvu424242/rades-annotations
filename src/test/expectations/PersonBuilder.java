package com.github.funthomas424242.domain;
import javax.annotation.Generated;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

@Generated(value="com.github.funthomas424242.rades.annotations.processors.RadesBuilderProcessor"
, comments="com.github.funthomas424242.domain.Person")
public class PersonBuilder {

    private Person person;

    public PersonBuilder(){
        this(new Person());
    }

    public PersonBuilder( final Person person ){
        this.person = person;
    }

    public Person build() {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        final java.util.Set<ConstraintViolation<Person>> constraintViolations = validator.validate(this.person);

        if (constraintViolations.size() > 0) {
            java.util.Set<String> violationMessages = new java.util.HashSet<String>();

            for (ConstraintViolation<?> constraintViolation : constraintViolations) {
                violationMessages.add(constraintViolation.getPropertyPath() + ": " + constraintViolation.getMessage());
            }

            throw new ValidationException("Person is not valid:\n" + StringUtils.join(violationMessages, "\n"));
        }
        final Person value = this.person;
        this.person = null;
        return value;
    }

    public PersonBuilder withLieblingsfarben( final java.util.Set lieblingsfarben ) {
        this.person.lieblingsfarben = lieblingsfarben;
        return this;
    }

    public PersonBuilder withBirthday( final java.time.LocalDate birthday ) {
        this.person.birthday = birthday;
        return this;
    }

    public PersonBuilder withGroesse( final int groesse ) {
        this.person.groesse = groesse;
        return this;
    }

    public PersonBuilder withName( final java.lang.String name ) {
        this.person.name = name;
        return this;
    }

    public PersonBuilder withVorname( final java.lang.String vorname ) {
        this.person.vorname = vorname;
        return this;
    }

}
