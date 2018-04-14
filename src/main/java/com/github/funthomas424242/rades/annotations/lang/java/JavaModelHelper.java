package com.github.funthomas424242.rades.annotations.lang.java;

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
