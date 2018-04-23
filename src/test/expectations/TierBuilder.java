package com.github.funthomas424242.domain;
import javax.annotation.Generated;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Generated(value="RadesBuilderProcessor"
        , date="2018-04-06T20:36:46.750"
        , comments="com.github.funthomas424242.domain.Tier")
public class TierBuilder {

    private Tier tier;

    public TierBuilder(){
        this(new Tier());
    }

    public TierBuilder( final Tier tier ){
        this.tier = tier;
    }

    public Tier build() {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        final java.util.Set<ConstraintViolation<Tier>> constraintViolations = validator.validate(this.tier);

        if (constraintViolations.size() > 0) {
            java.util.Set<String> violationMessages = new java.util.HashSet<String>();

            for (ConstraintViolation<?> constraintViolation : constraintViolations) {
                violationMessages.add(constraintViolation.getPropertyPath() + ": " + constraintViolation.getMessage());
            }

            throw new ValidationException("Tier is not valid:\n" + StringUtils.join(violationMessages, "\n"));
        }
        final Tier value = this.tier;
        this.tier = null;
        return value;
    }

    public <A> A build(Class<A> accessorClass) {
        final Tier tier = this.build();
        this.tier=tier;
        try {
            final Constructor<A> constructor=accessorClass.getDeclaredConstructor(Tier.class);
            final A accessor = constructor.newInstance(tier);
            this.tier=null;
            return accessor;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
