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
                .hasSourceEquivalentTo(JavaFileObjects.forSourceString("com.github.funthomas424242.domain.PersonBuilder", "package com.github.funthomas424242.domain;\n" +
                        "\n" +
                        "public class PersonBuilder {\n" +
                        "\n" +
                        "    private Person person = new Person();\n" +
                        "\n" +
                        "    public Person build() {\n" +
                        "        final Person value = this.person;\n" +
                        "        this.person = null;\n" +
                        "        return value;\n" +
                        "    }\n" +
                        "\n" +
                        "    public PersonBuilder withVorname( final String vorname ) {\n" +
                        "        this.person.vorname = vorname;\n" +
                        "        return this;\n" +
                        "    }\n" +
                        "\n" +
                        "    public PersonBuilder withName( final String name ) {\n" +
                        "        this.person.name = name;\n" +
                        "        return this;\n" +
                        "    }\n" +
                        "\n" +
                        "}\n")
                );
    }

    @Test
    public void shouldCompileClassWithoutIgnoreAnnotationWithoutErrors() throws MalformedURLException {

        final String projectSubfolder = "src/test/java/";
        final String resourcePath = "com/github/funthomas424242/domain/Person.java";
        final URL resourceURL = getResourceURL(projectSubfolder, resourcePath);


        Truth.assert_().about(javaSource())
                .that(JavaFileObjects.forResource(resourceURL))
                .processedWith(new RadesBuilderProcessor())
                .compilesWithoutError();
    }


}