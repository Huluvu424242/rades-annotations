package com.github.funthomas424242.rades.annotations.lang.java;

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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JavaModelHelper {

    public static String computePackageName(final String fullQualifiedClassName) {
        String packageName = null;
        int lastDot = fullQualifiedClassName.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = fullQualifiedClassName.substring(0, lastDot);
        }
        return packageName;
    }

    public static String getNowAsISOString() {
        final LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
