// TODO Vorbereitung für Unit Test mit compile-testing
//package com.github.funthomas424242.rades.annotations.processors;
//
//import com.google.testing.compile.Compilation;
//import com.google.testing.compile.JavaFileObjects;
//import org.junit.jupiter.api.Test;
//
//import static com.google.testing.compile.CompilationSubject.assertThat;
//import static com.google.testing.compile.Compiler.javac;
//
//class RadesBuilderProcessorTest {
//
//    @Test
//    void init() {
//    }
//
//    @Test
//    void process() {
//        final Compilation compilation = javac()
//                .withProcessors(new RadesBuilderProcessor())
//                .compile(JavaFileObjects.forSourceString("com.github.funthomas424242.domain.Person1","package com.github.funthomas424242.domain;\n" +
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
//
//        assertThat(compilation).succeeded();
//        assertThat(compilation)
//                .generatedSourceFile("com.github.funthomas424242.domain.Person1Builder")
//                .hasSourceEquivalentTo(JavaFileObjects.forResource("PersonBuilder.java"));
//
//
////        ASSERT.about(javaSource())
////                .that(JavaFileObjects.forResource("Person.java"))
////                .processedWith(new RadesBuilderProcessor())
////                .compilesWithoutError()
////                .and().generatesSources(JavaFileObjects.forResource("PersonBuilder.java"));
//    }
//}