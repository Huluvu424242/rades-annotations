package com.github.funthomas424242.rades.annotations.lang.java;

import javax.annotation.processing.Filer;

public interface JavaModelService {

    public JavaSrcFileCreator getJavaSrcFileCreator(final Filer filer, final String className);

}
