package com.github.funthomas424242.domain;

import com.github.funthomas424242.rades.annotations.accessors.RadesAddAccessor;
import com.github.funthomas424242.rades.annotations.accessors.RadesNoAccessor;
import com.github.funthomas424242.rades.annotations.builder.RadesAddBuilder;
import com.github.funthomas424242.rades.annotations.builder.RadesNoBuilder;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;


@RadesAddBuilder
@RadesAddAccessor
public class Person {

    public enum Farbe {
        ROT,
        GELB,
        GRÜN,
        BLAU,
        LILA,
        SCHWARZ,
        BRAUN,
        WEISS
    }

    private int id;

    @NotNull
    protected String name;

    // check for package scope
    String vorname;

    protected LocalDate birthday;

    public int groesse;

    @RadesNoAccessor
    @RadesNoBuilder
    protected final Set<Farbe> lieblingsfarben = new HashSet<>();

    protected Map<String, Person> freunde;


    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int computeAge() {
        if (this.birthday == null) {
            throw new IllegalStateException("birthday is unknow because is jet not set");
        }
        final Period period = birthday.until(LocalDate.now());
        return period.getYears();
    }

    public Stream<Farbe> getLieblingsfarben() {
        return this.lieblingsfarben.stream();
    }

    public void addLieblingsfarbe(final Farbe farbe) {

        this.lieblingsfarben.add(farbe);
    }

    public void removeLieblingsfarbe(final Farbe farbe) {
        this.lieblingsfarben.remove(farbe);
    }

    public void addFreund(final String alias, final Person freund) {
        this.freunde.put(alias, freund);
    }

    @Override
    public String toString() {
        return name + ", " + vorname;
    }
}
