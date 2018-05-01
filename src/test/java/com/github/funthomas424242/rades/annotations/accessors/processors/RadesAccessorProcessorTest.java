package com.github.funthomas424242.rades.annotations.accessors.processors;

/*-
 * #%L
 * rades-annotations
 * %%
 * Copyright (C) 2018 PIUG
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import com.github.funthomas424242.rades.annotations.accessors.model.java.AccessorInjectionServiceProvider;
import com.github.funthomas424242.rades.annotations.accessors.model.java.AccessorSrcFileCreator;
import com.google.common.truth.ExpectFailure;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import javax.annotation.processing.Filer;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.github.funthomas424242.rades.annotations.lang.java.AssertionHelper.assertThat;
import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.Compiler.javac;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public class RadesAccessorProcessorTest {

    protected final String NONE_WRITEABLE_ACCESSOR_JAVA = "package com.github.funthomas424242.domain;\n" +
            "import javax.annotation.Generated;\n" +
            "\n" +
            "@Generated(value=\"RadesAccessorProcessor\"\n" +
            ", date=\"2018-04-27T20:24:57.102\"\n" +
            ", comments=\"com.github.funthomas424242.domain.NoneWriteable\")\n" +
            "public class NoneWriteableAccessor {\n" +
            "\n" +
            "    protected final NoneWriteable noneWriteable;\n" +
            "\n" +
            "    public NoneWriteableAccessor( final NoneWriteable noneWriteable ){\n" +
            "        this.noneWriteable = noneWriteable;\n" +
            "    }\n" +
            "\n" +
            "    public NoneWriteable toNoneWriteable(){\n" +
            "        return this.noneWriteable;\n" +
            "    }\n" +
            "\n" +
            "    public String toString(){\n" +
            "        return this.noneWriteable.toString();\n" +
            "    }\n" +
            "\n" +
            "}\n";


    protected static final String TEST_SRC_FOLDER = "src/test/java/";
    protected static final String TEST_EXPECTATION_FOLDER = "src/test/expectations/";

    protected static URL urlPersonJava;
    protected static URL urlPersonAccessorJava;
    protected static URL urlFirmaJava;
    protected static URL urlFirmaAccessorJava;
    protected static URL urlAutoJava;
    protected static URL urlAutoAccessorJava;
    protected static URL urlTierJava;
    protected static URL urlTierAccessorJava;
    protected static URL urlMetaAnnotationJava;
    protected static URL urlNonePackageClassJava;
    protected static URL urlNoneWriteableAccessorJava;


    @Rule
    public final ExpectFailure expectFailure = new ExpectFailure();

    static class DefaultJavaModelProvider extends AccessorInjectionServiceProvider {
        @Override
        public String getNowAsISOString() {
            return "2018-04-06T20:36:46.750";
        }
    }


    static class NoWritableJavaModelProvider extends AccessorInjectionServiceProvider {
        @Override
        public AccessorSrcFileCreator getJavaSrcFileCreator(final Filer filer, final String className) {
            final AccessorSrcFileCreator creator = new AccessorSrcFileCreator(filer, className, this) {
                @Override
                public void init() throws IOException {
                    throw new IOException("Medium ist schreibgeschützt!");
                }
            };
            return creator;
        }
    }

    static class NoClosebleJavaModelProvider extends AccessorInjectionServiceProvider {
        @Override
        public AccessorSrcFileCreator getJavaSrcFileCreator(final Filer filer, final String className) {
            final AccessorSrcFileCreator creator = new AccessorSrcFileCreator(filer, className, this) {
                @Override
                public void close() throws Exception {
                    throw new Exception("Kann Stream nicht schließen!");
                }
            };
            return creator;
        }
    }


    protected static URL getResourceURL(String projectSrcRoot, String resourcePath) throws MalformedURLException {
        final Path tmpPath = Paths.get(projectSrcRoot + resourcePath);
        final URL resourceURL = tmpPath.toAbsolutePath().toUri().toURL();
        return resourceURL;
    }


    @BeforeClass
    public static void setUp() throws MalformedURLException {
        urlPersonJava = getResourceURL(TEST_SRC_FOLDER, "com/github/funthomas424242/domain/Person.java");
        urlPersonAccessorJava = getResourceURL(TEST_EXPECTATION_FOLDER, "PersonAccessor.java");
        urlFirmaJava = getResourceURL(TEST_SRC_FOLDER, "com/github/funthomas424242/domain/Firma.java");
        urlFirmaAccessorJava = getResourceURL(TEST_EXPECTATION_FOLDER, "FirmaAGZugreifer.java");
        urlAutoJava = getResourceURL(TEST_SRC_FOLDER, "com/github/funthomas424242/domain/Auto.java");
        urlAutoAccessorJava = getResourceURL(TEST_EXPECTATION_FOLDER, "CarAccessor.java");
        urlTierJava = getResourceURL(TEST_SRC_FOLDER, "com/github/funthomas424242/domain/Tier.java");
        urlTierAccessorJava = getResourceURL(TEST_EXPECTATION_FOLDER, "TierAccessor.java");
        urlMetaAnnotationJava = getResourceURL(TEST_SRC_FOLDER, "com/github/funthomas424242/rades/annotations/accessors/AccessorMetaAnnotation.java");
        urlNonePackageClassJava = getResourceURL(TEST_EXPECTATION_FOLDER, "NonePackageClass.java");
        urlNoneWriteableAccessorJava = getResourceURL(TEST_EXPECTATION_FOLDER, "NoneWriteableAccessor.java");
    }


    @Test
    public void processPerson() throws MalformedURLException {
        final RadesAccessorProcessor processor = new RadesAccessorProcessor();
        processor.setJavaModelService(new DefaultJavaModelProvider());

        final Compilation compilation = javac()
                .withProcessors(processor)
                .compile(JavaFileObjects.forResource(urlPersonJava));
        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("com.github.funthomas424242.domain.PersonAccessor")
                .hasSourceEquivalentTo(JavaFileObjects.forResource(urlPersonAccessorJava)
                );

        // equivalent mitl altem API
        final RadesAccessorProcessor processor1 = new RadesAccessorProcessor();
        processor1.setJavaModelService(new DefaultJavaModelProvider());

        assertAbout(javaSource())
                .that(JavaFileObjects.forResource(urlPersonJava))
                .processedWith(processor1)
                .compilesWithoutError()
                .and()
                .generatesSources(JavaFileObjects.forResource(urlPersonAccessorJava));


    }

    @Test
    public void processFirma() throws MalformedURLException {
        final RadesAccessorProcessor processor = new RadesAccessorProcessor();
        processor.setJavaModelService(new DefaultJavaModelProvider());

        final Compilation compilation = javac()
                .withProcessors(processor)
                .compile(JavaFileObjects.forResource(urlFirmaJava));
        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("com.github.funthomas424242.domain.FirmaAGZugreifer")
                .hasSourceEquivalentTo(JavaFileObjects.forResource(urlFirmaAccessorJava)
                );
    }

    @Test
    public void processAuto() throws MalformedURLException {
        final RadesAccessorProcessor processor = new RadesAccessorProcessor();
        processor.setJavaModelService(new DefaultJavaModelProvider());

        final Compilation compilation = javac()
                .withProcessors(processor)
                .compile(JavaFileObjects.forResource(urlAutoJava));
        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("com.github.funthomas424242.domain.CarAccessor")
                .hasSourceEquivalentTo(JavaFileObjects.forResource(urlAutoAccessorJava)
                );
    }

    @Test
    public void processTier() throws MalformedURLException {
        final RadesAccessorProcessor processor = new RadesAccessorProcessor();
        processor.setJavaModelService(new DefaultJavaModelProvider());

        final Compilation compilation = javac()
                .withProcessors(processor)
                .compile(JavaFileObjects.forResource(urlTierJava));
        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("com.github.funthomas424242.domain.TierAccessor")
                .hasSourceEquivalentTo(JavaFileObjects.forResource(urlTierAccessorJava)
                );
    }


    @Test
    public void shouldCompilePersonJavaWithoutErrors() {
        final RadesAccessorProcessor processor = new RadesAccessorProcessor();
        processor.setJavaModelService(new DefaultJavaModelProvider());

        assertAbout(javaSource())
                .that(JavaFileObjects.forResource(urlPersonJava))
                .processedWith(processor)
                .compilesWithoutError();

    }

    @Test
    public void shouldCompileMetaAnnotationJavaWithoutErrors() {
        final RadesAccessorProcessor processor = new RadesAccessorProcessor();
        processor.setJavaModelService(new DefaultJavaModelProvider());

        assertAbout(javaSource())
                .that(JavaFileObjects.forResource(urlMetaAnnotationJava))
                .processedWith(processor)
                .compilesWithoutError();

    }


    @Test
    public void shouldCompileNonPackageClassWithoutErrors() {
        final RadesAccessorProcessor processor = new RadesAccessorProcessor();
        processor.setJavaModelService(new DefaultJavaModelProvider());

        assertAbout(javaSource())
                .that(JavaFileObjects.forSourceString("NonePackageClass", "\n" +
                        "@com.github.funthomas424242.rades.annotations.accessors.RadesAddAccessor\n" +
                        "public class NonePackageClass {\n" +
                        "}\n"))
                .processedWith(processor)
                .compilesWithoutError();

    }


    @Test
    public void shouldFailToWriteNoneWriteableAccessorClass() {

        final RadesAccessorProcessor processor = new RadesAccessorProcessor();
        processor.setJavaModelService(new NoWritableJavaModelProvider());

        expectFailure.whenTesting()
                .about(javaSource())
                .that(JavaFileObjects.forSourceString("com.github.funthomas424242.domain.NoneWriteable", "\n" +
                        "package com.github.funthomas424242.domain;\n" +
                        "@com.github.funthomas424242.rades.annotations.accessors.RadesAddAccessor\n" +
                        "public class NoneWriteable {\n" +
                        "}\n"))
                .processedWith(processor)
                .compilesWithoutError()
                .and()
                .generatesFiles(JavaFileObjects.forSourceString(
                        "com.github.funthomas424242.domain.NoneWriteableAccessor", NONE_WRITEABLE_ACCESSOR_JAVA));

        final AssertionError expected = expectFailure.getFailure();
        assertThat(expected.getMessage()).contains("Did not find a generated file corresponding to com/github/funthomas424242/domain/NoneWriteableAccessor.java");
    }


    @Test(expected = NullPointerException.class)
    public void shouldFailToCloseNoneCloseableAccessorClass() {

        final RadesAccessorProcessor processor = new RadesAccessorProcessor();
        processor.setJavaModelService(new NoClosebleJavaModelProvider());

        assertAbout(javaSource())
                .that(JavaFileObjects.forResource(urlPersonJava))
                .processedWith(processor)
                .compilesWithoutError()
                .and()
                .generatesSources(JavaFileObjects.forResource(urlPersonAccessorJava));
    }
}
