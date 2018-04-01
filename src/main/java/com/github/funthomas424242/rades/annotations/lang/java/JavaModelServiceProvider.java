package com.github.funthomas424242.rades.annotations.lang.java;

import com.google.auto.service.AutoService;

import javax.annotation.processing.Filer;
import java.io.IOException;

@AutoService(JavaModelService.class)
public class JavaModelServiceProvider implements JavaModelService{

    @Override
    public JavaSrcFileCreator getJavaSrcFileCreator(final Filer filer, final String className) throws IOException {
        return new JavaSrcFileCreator(filer, className);
    }

}
