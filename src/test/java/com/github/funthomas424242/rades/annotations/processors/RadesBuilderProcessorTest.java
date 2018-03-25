//TODO Vorbereitung für Unit Test mit compile-testing
package com.github.funthomas424242.rades.annotations.processors;

import com.google.common.truth.Truth;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

//import com.google.common.truth.Truth;
//import com.google.testing.compile.Compilation;
// import org.junit.jupiter.api.Test;

public class RadesBuilderProcessorTest {

    protected URL getResourceURL(String projectSrcRoot, String resourcePath) throws MalformedURLException {
        final Path tmpPath = Paths.get(projectSrcRoot + resourcePath);
        final URL resourceURL = tmpPath.toAbsolutePath().toUri().toURL();
        System.out.println("Resource:" + resourceURL);
        return resourceURL;
    }

    @Test
    public void initTest() {
    }

//    @Test
//    public void processTest() {
//        final Compilation compilation = javac()
//                .withProcessors(new RadesBuilderProcessor())
//                .compile(JavaFileObjects.forResource("Person.java"));
//                .compile(JavaFileObjects.forSourceString("com.github.funthomas424242.domain.Person1", "package com.github.funthomas424242.domain;\n" +
//                        "\n" +
//                        "import com.github.funthomas424242.rades.annotations.RadesBuilder;\n" +
//                        "\n" +
//                        "import java.util.Date;\n" +
//                        "\n" +
//                        "@RadesBuilder\n" +
//                        "public class Person1 {\n" +
//                        "\n" +
//                        "    private int id;\n" +
//                        "\n" +
//                        "    protected String name;\n" +
//                        "\n" +
//                        "    protected String vorname;\n" +
//                        "\n" +
//                        "//    protected Date birthday;\n" +
//                        "\n" +
//                        "\n" +
//                        "}\n"));

//        assertThat(compilation).succeeded();
//        assertThat(compilation)
//                .generatedSourceFile("com.github.funthomas424242.domain.Person1Builder")
//                .hasSourceEquivalentTo(JavaFileObjects.forResource("PersonBuilder.java"));
//

//        ASSERT.about(javaSource())
//                .that(JavaFileObjects.forResource("Person.java"))
//                .processedWith(new RadesBuilderProcessor())
//                .compilesWithoutError()
//                .and().generatesSources(JavaFileObjects.forResource("PersonBuilder.java"));
//    }

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