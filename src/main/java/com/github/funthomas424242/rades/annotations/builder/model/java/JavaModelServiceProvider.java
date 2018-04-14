package com.github.funthomas424242.rades.annotations.builder.model.java;

import com.github.funthomas424242.rades.annotations.lang.java.JavaModelHelper;
import com.google.auto.service.AutoService;

import javax.annotation.processing.Filer;

@AutoService(JavaModelService.class)
public class JavaModelServiceProvider implements JavaModelService {

    @Override
    public JavaSrcFileCreator getJavaSrcFileCreator(final Filer filer, final String className) {
        return new JavaSrcFileCreator(filer, className, this);
    }

    @Override
    public String getNowAsISOString() {
        return JavaModelHelper.getNowAsISOString();
    }

}
