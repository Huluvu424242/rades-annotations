package com.github.funthomas424242.rades.annotations.accessors.model.java;

import javax.annotation.processing.Filer;

public interface AccessorInjectionService {

    AccessorSrcFileCreator getJavaSrcFileCreator(final Filer filer, final String className);

    String getNowAsISOString();

}
