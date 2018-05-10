package com.github.funthomas424242.domain;

/*-
 * #%L
 * rades-annotations
 * %%
 * Copyright (C) 2018 PIUG
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import com.github.funthomas424242.rades.annotations.accessors.NoAccessor;
import com.github.funthomas424242.rades.annotations.accessors.RadesAddAccessor;
import com.github.funthomas424242.rades.annotations.accessors.RadesNoAccessor;
import com.github.funthomas424242.rades.annotations.builder.NoBuilder;
import com.github.funthomas424242.rades.annotations.builder.RadesAddBuilder;
import com.github.funthomas424242.rades.annotations.builder.RadesNoBuilder;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
    @NotNull
    protected final Set<Farbe> lieblingsfarben = new HashSet<>();

    @NotNull
    protected Collection<Person> freunde = new ArrayList<>();

    @NoAccessor
    @NoBuilder
    @NotNull
    protected Map<String, LocalDate> visitedPlaces = new HashMap<>();


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

    @Override
    public String toString() {
        return name + ", " + vorname;
    }
}
