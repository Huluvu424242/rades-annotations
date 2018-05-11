package com.github.funthomas424242.domain;

import com.github.funthomas424242.rades.annotations.accessors.InvalidAccessorException;
import javax.annotation.Generated;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Generated(value="RadesBuilderProcessor"
, date="2018-04-06T20:36:46.750"
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

            final StringBuffer buf = new StringBuffer();
            buf.append("Person is not valid:\n");
            violationMessages.forEach(message -> buf.append(message + "\n"));
            throw new ValidationException(buf.toString());
        }
        final Person value = this.person;
        this.person = null;
        return value;
    }

    public <A> A build(Class<A> accessorClass) {
        final Person person = this.build();
        this.person=person;
        try{
            final Constructor<A> constructor=accessorClass.getDeclaredConstructor(Person.class);
            final A accessor = constructor.newInstance(person);
            this.person=null;
            return accessor;
        }catch(NoSuchMethodException | IllegalAccessException|  InstantiationException|  InvocationTargetException ex){
            throw new InvalidAccessorException("ungültige Accessorklasse übergeben",ex);
        }
    }

    public PersonBuilder withBirthday( final java.time.LocalDate birthday ) {
        this.person.birthday = birthday;
        return this;
    }

    public PersonBuilder withVisitedPlaces( final java.util.Map<java.lang.String,java.time.LocalDate> visitedPlaces ) {
        this.person.visitedPlaces = visitedPlaces;
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
