package com.github.funthomas424242.rades.annotations.builder.model.java;

import javax.annotation.processing.Filer;

public interface BuilderInjectionService {

    BuilderSrcFileCreator getJavaSrcFileCreator(final Filer filer, final String className);

    String getNowAsISOString();

}
