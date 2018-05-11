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
, comments="com.github.funthomas424242.domain.Firma")
public class FirmaAGErbauer {

    private Firma firma;

    public FirmaAGErbauer(){
        this(new Firma());
    }

    public FirmaAGErbauer( final Firma firma ){
        this.firma = firma;
    }

    public Firma build() {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        final java.util.Set<ConstraintViolation<Firma>> constraintViolations = validator.validate(this.firma);

        if (constraintViolations.size() > 0) {
            java.util.Set<String> violationMessages = new java.util.HashSet<String>();

            for (ConstraintViolation<?> constraintViolation : constraintViolations) {
                violationMessages.add(constraintViolation.getPropertyPath() + ": " + constraintViolation.getMessage());
            }

            final StringBuffer buf = new StringBuffer();
            buf.append("Firma is not valid:\n");
            violationMessages.forEach(message -> buf.append(message + "\n"));
            throw new ValidationException(buf.toString());
        }
        final Firma value = this.firma;
        this.firma = null;
        return value;
    }

    public <A> A build(Class<A> accessorClass) {
        final Firma firma = this.build();
        this.firma=firma;
        try{
            final Constructor<A> constructor=accessorClass.getDeclaredConstructor(Firma.class);
            final A accessor = constructor.newInstance(firma);
            this.firma=null;
            return accessor;
        }catch(NoSuchMethodException | IllegalAccessException|  InstantiationException|  InvocationTargetException ex){
            throw new InvalidAccessorException("ungültige Accessorklasse übergeben",ex);
        }
    }

    public FirmaAGErbauer withGruendungstag( final java.util.Date gruendungstag ) {
        this.firma.gruendungstag = gruendungstag;
        return this;
    }

    public FirmaAGErbauer withBetriebeNr( final java.lang.String betriebeNr ) {
        this.firma.betriebeNr = betriebeNr;
        return this;
    }

    public FirmaAGErbauer withName( final java.lang.String name ) {
        this.firma.name = name;
        return this;
    }

}
