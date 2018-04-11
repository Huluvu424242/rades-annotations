package com.github.funthomas424242.rades.annotations.builder.model.java;

import com.github.funthomas424242.rades.annotations.builder.model.java.JavaModelService;
import com.github.funthomas424242.rades.annotations.builder.model.java.JavaSrcFileCreator;
import com.google.auto.service.AutoService;

import javax.annotation.processing.Filer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AutoService(JavaModelService.class)
public class JavaModelServiceProvider implements JavaModelService {

    @Override
    public JavaSrcFileCreator getJavaSrcFileCreator(final Filer filer, final String className) {
        return new JavaSrcFileCreator(filer, className,this);
    }

    @Override
    public String getNowAsISOString() {
        final LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ISO_DATE_TIME);
    }

}
