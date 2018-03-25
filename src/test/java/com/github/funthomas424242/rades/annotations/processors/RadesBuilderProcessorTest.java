//TODO Vorbereitung für Unit Test mit compile-testing
package com.github.funthomas424242.rades.annotations.processors;

import com.github.funthomas424242.TestMetaAnnotation;
import com.github.funthomas424242.domain.Person;
import com.github.funthomas424242.rades.annotations.processors.RadesBuilderProcessor;
//import com.google.common.truth.Truth;
//import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;
// import org.junit.jupiter.api.Test;

import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import javax.tools.FileObject;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static org.junit.Assert.assertNotNull;
import static org.truth0.Truth.ASSERT;
//import static org.truth0.Truth.ASSERT;

public class RadesBuilderProcessorTest {

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

        ClassLoader cl = ClassLoader.getSystemClassLoader();

        URL[] urls = ((URLClassLoader)cl).getURLs();

        for(URL url: urls){
            System.out.println(url.getFile());
        }

        final Path tmpPath=Paths.get("src/test/java/com/github/funthomas424242/domain/Person.java");
        final FileObject resource=JavaFileObjects.forResource(tmpPath.toAbsolutePath().toUri().toURL());
        assertNotNull(resource);


        final FileSystem defaultFileSystem=FileSystems.getDefault();
        assertNotNull(defaultFileSystem);
        final Path baseDir = defaultFileSystem.getPath(".");
        System.out.println("DIR:"+baseDir.toAbsolutePath().toString());

        final Path path=Paths.get("src/test/java/com/github/funthomas424242/domain/Person.java");
        assertNotNull(path);
        final Path absolutePath=path.toAbsolutePath();
        assertNotNull(absolutePath);
        final URI uri = absolutePath.toUri();
        System.out.println("Path:"+absolutePath);
        assertNotNull(uri);
        final URL url=uri.toURL();
        assertNotNull(url);
        System.out.println("URL:"+url);


        ASSERT.about(javaSource())
                .that(JavaFileObjects.forResource(url))
                .processedWith(new RadesBuilderProcessor())
                .compilesWithoutError();
    }


}