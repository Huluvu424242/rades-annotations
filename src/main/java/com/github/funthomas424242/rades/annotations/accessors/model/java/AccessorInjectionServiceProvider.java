package com.github.funthomas424242.rades.annotations.accessors.model.java;

import com.github.funthomas424242.rades.annotations.lang.java.JavaModelHelper;
import com.google.auto.service.AutoService;

import javax.annotation.processing.Filer;

@AutoService(AccessorInjectionService.class)
public class AccessorInjectionServiceProvider implements AccessorInjectionService {

    @Override
    public AccessorSrcFileCreator getJavaSrcFileCreator(final Filer filer, final String className) {
        return new AccessorSrcFileCreator(filer, className, this);
    }

    @Override
    public String getNowAsISOString() {
        return JavaModelHelper.getNowAsISOString();
    }

}
