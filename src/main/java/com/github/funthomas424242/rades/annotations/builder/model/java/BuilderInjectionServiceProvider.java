package com.github.funthomas424242.rades.annotations.builder.model.java;

import com.github.funthomas424242.rades.annotations.lang.java.JavaModelHelper;
import com.google.auto.service.AutoService;

import javax.annotation.processing.Filer;

@AutoService(BuilderInjectionService.class)
public class BuilderInjectionServiceProvider implements BuilderInjectionService {

    @Override
    public BuilderSrcFileCreator getJavaSrcFileCreator(final Filer filer, final String className) {
        return new BuilderSrcFileCreator(filer, className, this);
    }

    @Override
    public String getNowAsISOString() {
        return JavaModelHelper.getNowAsISOString();
    }

}
