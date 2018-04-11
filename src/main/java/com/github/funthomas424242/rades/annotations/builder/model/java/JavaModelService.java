package com.github.funthomas424242.rades.annotations.builder.model.java;

import com.github.funthomas424242.rades.annotations.builder.model.java.JavaSrcFileCreator;

import javax.annotation.processing.Filer;

public interface JavaModelService {

    JavaSrcFileCreator getJavaSrcFileCreator(final Filer filer, final String className);

    String getNowAsISOString();

}
