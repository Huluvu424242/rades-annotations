//TODO Vorbereitung für Unit Test mit compile-testing
package com.github.funthomas424242.rades.annotations.processors;

import com.google.common.truth.Truth;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public class RadesBuilderProcessorTest {

    protected static final String TEST_SRC_FOLDER = "src/test/java/";
    protected static final String TEST_EXPECTATION_FOLDER = "src/test/expectations/";

    protected static URL urlPersonJava;
    protected static URL urlPersonBuilderJava;
    protected static URL urlMetaAnnotationJava;
    protected static URL urlNonePackageClassJava;


    protected static URL getResourceURL(String projectSrcRoot, String resourcePath) throws MalformedURLException {
        final Path tmpPath = Paths.get(projectSrcRoot + resourcePath);
        final URL resourceURL = tmpPath.toAbsolutePath().toUri().toURL();
        System.out.println("Resource:" + resourceURL);
        return resourceURL;
    }

    @BeforeClass
    public static void setUp() throws MalformedURLException {
        urlPersonJava = getResourceURL(TEST_SRC_FOLDER, "com/github/funthomas424242/domain/Person.java");
        urlMetaAnnotationJava = getResourceURL(TEST_SRC_FOLDER, "com/github/funthomas424242/MetaAnnotation.java");
        urlPersonBuilderJava = getResourceURL(TEST_EXPECTATION_FOLDER, "PersonBuilder.java");
        urlNonePackageClassJava = getResourceURL(TEST_EXPECTATION_FOLDER, "NonePackageClass.java");
    }


    @Test
    public void processTest() throws MalformedURLException {


        final Compilation compilation = javac()
                .withProcessors(new RadesBuilderProcessor())
                .compile(JavaFileObjects.forResource(urlPersonJava));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("com.github.funthomas424242.domain.PersonBuilder")
                .hasSourceEquivalentTo(JavaFileObjects.forResource(urlPersonBuilderJava)
                );
    }

    @Test
    public void shouldCompilePersonJavaWithoutErrors() {

        Truth.assert_().about(javaSource())
                .that(JavaFileObjects.forResource(urlPersonJava))
                .processedWith(new RadesBuilderProcessor())
                .compilesWithoutError();

    }

    @Test
    public void shouldCompileMetaAnnotationJavaWithoutErrors() {

        Truth.assert_().about(javaSource())
                .that(JavaFileObjects.forResource(urlMetaAnnotationJava))
                .processedWith(new RadesBuilderProcessor())
                .compilesWithoutError();

    }


    @Test
    public void shouldCompileNonPackageClassWithoutErrors() {

        assertAbout(javaSource())
                .that(JavaFileObjects.forSourceString("NonePackageClass","\n" +
                        "@com.github.funthomas424242.rades.annotations.RadesBuilder\n" +
                        "public class NonePackageClass {\n" +
                        "}\n"))
                .processedWith(new RadesBuilderProcessor())
                .compilesWithoutError();

    }

}