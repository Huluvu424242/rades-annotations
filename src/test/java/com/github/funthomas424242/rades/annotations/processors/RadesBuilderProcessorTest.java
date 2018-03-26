//TODO Vorbereitung für Unit Test mit compile-testing
package com.github.funthomas424242.rades.annotations.processors;

import com.google.common.truth.Truth;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import javax.tools.StandardLocation;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static org.junit.Assert.fail;

public class RadesBuilderProcessorTest {

    protected URL getResourceURL(String projectSrcRoot, String resourcePath) throws MalformedURLException {
        final Path tmpPath = Paths.get(projectSrcRoot + resourcePath);
        final URL resourceURL = tmpPath.toAbsolutePath().toUri().toURL();
        System.out.println("Resource:" + resourceURL);
        return resourceURL;
    }


    @Test
    public void processTest() throws MalformedURLException {
        final String testFolderRoot = "src/test/java/";
        final String pathPersonJava = "com/github/funthomas424242/domain/Person.java";
        final URL urlPersonJava = getResourceURL(testFolderRoot, pathPersonJava);

        final Compilation compilation = javac()
                .withProcessors(new RadesBuilderProcessor())
                .compile(JavaFileObjects.forResource(urlPersonJava));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedFile(StandardLocation.SOURCE_OUTPUT, "com.github.funthomas424242.domain", "PersonBuilder.java")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("expectations/PersonBuilder.java")
                );
    }

    @Test
    public void shouldCompileClassWithoutIgnoreAnnotationWithoutErrors() {

        final String projectSubfolder = "src/test/java/";
        final String resourcePath = "com/github/funthomas424242/domain/Person.java";
        try {
            final URL resourceURL = getResourceURL(projectSubfolder, resourcePath);


            Truth.assert_().about(javaSource())
                    .that(JavaFileObjects.forResource(resourceURL))
                    .processedWith(new RadesBuilderProcessor())
                    .compilesWithoutError();
        } catch (MalformedURLException ex) {
            fail();
        }
    }


}