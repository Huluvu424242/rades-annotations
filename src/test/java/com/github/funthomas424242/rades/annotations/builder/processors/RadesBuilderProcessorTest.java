package com.github.funthomas424242.rades.annotations.builder.processors;

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

import com.github.funthomas424242.rades.annotations.builder.model.java.BuilderInjectionServiceProvider;
import com.github.funthomas424242.rades.annotations.builder.model.java.BuilderSrcFileCreator;
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

public class RadesBuilderProcessorTest {

    protected final String NONE_WRITEABLE_BUILDER_JAVA = "package com.github.funthomas424242.domain;\n" +
            "import javax.annotation.Generated;\n" +
            "import org.apache.commons.lang3.StringUtils;\n" +
            "\n" +
            "import javax.validation.ConstraintViolation;\n" +
            "import javax.validation.Validation;\n" +
            "import javax.validation.ValidationException;\n" +
            "import javax.validation.Validator;\n" +
            "\n" +
            "@Generated(value=\"RadesBuilderProcessor\"\n" +
            ", comments=\"com.github.funthomas424242.domain.NoneWriteable\")\n" +
            "public class NoneWriteableBuilder {\n" +
            "\n" +
            "    private NoneWriteable noneWriteable;\n" +
            "\n" +
            "    public NoneWriteableBuilder(){\n" +
            "        this(new NoneWriteable());\n" +
            "    }\n" +
            "\n" +
            "    public NoneWriteableBuilder( final NoneWriteable noneWriteable ){\n" +
            "        this.noneWriteable = noneWriteable;\n" +
            "    }\n" +
            "\n" +
            "    public NoneWriteable build() {\n" +
            "        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();\n" +
            "        final java.util.Set<ConstraintViolation<NoneWriteable>> constraintViolations = validator.validate(this.noneWriteable);\n" +
            "\n" +
            "        if (constraintViolations.size() > 0) {\n" +
            "            java.util.Set<String> violationMessages = new java.util.HashSet<String>();\n" +
            "\n" +
            "            for (ConstraintViolation<?> constraintViolation : constraintViolations) {\n" +
            "                violationMessages.add(constraintViolation.getPropertyPath() + \": \" + constraintViolation.getMessage());\n" +
            "            }\n" +
            "\n" +
            "            throw new ValidationException(\"NoneWriteable is not valid:\\n\" + StringUtils.join(violationMessages, \"\\n\"));\n" +
            "        }\n" +
            "        final NoneWriteable value = this.noneWriteable;\n" +
            "        this.noneWriteable = null;\n" +
            "        return value;\n" +
            "    }\n" +
            "\n" +
            "}\n";


    protected static final String TEST_SRC_FOLDER = "src/test/java/";
    protected static final String TEST_EXPECTATION_FOLDER = "src/test/expectations/";
    protected static final String TEST_GENERATION_FOLDER = "target/generated-test-sources/test-annotations/";

    protected static URL urlPersonJava;
    protected static URL urlPersonBuilderJava;
    protected static URL urlFirmaJava;
    protected static URL urlFirmaBuilderJava;
    protected static URL urlAutoJava;
    protected static URL urlAutoBuilderJava;
    protected static URL urlTierJava;
    protected static URL urlTierBuilderJava;
    protected static URL urlKatzeJava;
    protected static URL urlKatzeBuilderJava;
    protected static URL urlMetaAnnotationJava;
    protected static URL urlNonePackageClassJava;
    protected static URL urlNoneWriteableBuilderJava;


    @Rule
    public final ExpectFailure expectFailure = new ExpectFailure();

    static class DefaultJavaModelProvider extends BuilderInjectionServiceProvider {
        @Override
        public String getNowAsISOString() {
            return "2018-04-06T20:36:46.750";
        }
    }


    static class NoWritableJavaModelProvider extends BuilderInjectionServiceProvider {
        @Override
        public BuilderSrcFileCreator getJavaSrcFileCreator(final Filer filer, final String className) {
            final BuilderSrcFileCreator creator = new BuilderSrcFileCreator(filer, className, this) {
                @Override
                public void init() throws IOException {
                    throw new IOException("Medium ist schreibgeschützt!");
                }
            };
            return creator;
        }
    }

    static class NoClosebleJavaModelProvider extends BuilderInjectionServiceProvider {
        @Override
        public BuilderSrcFileCreator getJavaSrcFileCreator(final Filer filer, final String className) {
            final BuilderSrcFileCreator creator = new BuilderSrcFileCreator(filer, className, this) {
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
        urlPersonBuilderJava = getResourceURL(TEST_EXPECTATION_FOLDER, "PersonBuilder.java");
        urlFirmaJava = getResourceURL(TEST_SRC_FOLDER, "com/github/funthomas424242/domain/Firma.java");
        urlFirmaBuilderJava = getResourceURL(TEST_EXPECTATION_FOLDER, "FirmaAGErbauer.java");
        urlAutoJava = getResourceURL(TEST_SRC_FOLDER, "com/github/funthomas424242/domain/Auto.java");
        urlAutoBuilderJava = getResourceURL(TEST_EXPECTATION_FOLDER, "CarBuilder.java");
        urlTierJava = getResourceURL(TEST_SRC_FOLDER, "com/github/funthomas424242/domain/Tier.java");
        urlTierBuilderJava = getResourceURL(TEST_EXPECTATION_FOLDER, "TierBuilder.java");
        urlKatzeJava = getResourceURL(TEST_SRC_FOLDER, "com/github/funthomas424242/domain/Katze.java");
        urlKatzeBuilderJava = getResourceURL(TEST_EXPECTATION_FOLDER, "KatzeBuilder.java");
        urlMetaAnnotationJava = getResourceURL(TEST_SRC_FOLDER, "com/github/funthomas424242/rades/annotations/builder/BuilderMetaAnnotation.java");
        urlNonePackageClassJava = getResourceURL(TEST_EXPECTATION_FOLDER, "NonePackageClass.java");
        urlNoneWriteableBuilderJava = getResourceURL(TEST_EXPECTATION_FOLDER, "NoneWriteableBuilder.java");
    }


    @Test
    public void processPerson() throws MalformedURLException {
        final RadesBuilderProcessor processor = new RadesBuilderProcessor();
        processor.setJavaModelService(new DefaultJavaModelProvider());

        final Compilation compilation = javac()
                .withProcessors(processor)
                .compile(JavaFileObjects.forResource(urlPersonJava));
        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("com.github.funthomas424242.domain.PersonBuilder")
                .hasSourceEquivalentTo(JavaFileObjects.forResource(urlPersonBuilderJava)
                );

        // equivalent mitl altem API
        final RadesBuilderProcessor processor1 = new RadesBuilderProcessor();
        processor1.setJavaModelService(new DefaultJavaModelProvider());

        assertAbout(javaSource())
                .that(JavaFileObjects.forResource(urlPersonJava))
                .processedWith(processor1)
                .compilesWithoutError()
                .and()
                .generatesSources(JavaFileObjects.forResource(urlPersonBuilderJava));


    }

    @Test
    public void processFirma() throws MalformedURLException {
        final RadesBuilderProcessor processor = new RadesBuilderProcessor();
        processor.setJavaModelService(new DefaultJavaModelProvider());

        final Compilation compilation = javac()
                .withProcessors(processor)
                .compile(JavaFileObjects.forResource(urlFirmaJava));
        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("com.github.funthomas424242.domain.FirmaAGErbauer")
                .hasSourceEquivalentTo(JavaFileObjects.forResource(urlFirmaBuilderJava)
                );
    }

    @Test
    public void processAuto() throws MalformedURLException {
        final RadesBuilderProcessor processor = new RadesBuilderProcessor();
        processor.setJavaModelService(new DefaultJavaModelProvider());

        final Compilation compilation = javac()
                .withProcessors(processor)
                .compile(JavaFileObjects.forResource(urlAutoJava));
        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("com.github.funthomas424242.domain.CarBuilder")
                .hasSourceEquivalentTo(JavaFileObjects.forResource(urlAutoBuilderJava)
                );
    }

    @Test
    public void processTier() throws MalformedURLException {
        final RadesBuilderProcessor processor = new RadesBuilderProcessor();
        processor.setJavaModelService(new DefaultJavaModelProvider());

        final Compilation compilation = javac()
                .withProcessors(processor)
                .compile(JavaFileObjects.forResource(urlTierJava));
        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("com.github.funthomas424242.domain.TierBuilder")
                .hasSourceEquivalentTo(JavaFileObjects.forResource(urlTierBuilderJava)
                );
    }


    @Test
    public void shouldCompilePersonJavaWithoutErrors() {
        final RadesBuilderProcessor processor = new RadesBuilderProcessor();
        processor.setJavaModelService(new DefaultJavaModelProvider());

        assertAbout(javaSource())
                .that(JavaFileObjects.forResource(urlPersonJava))
                .processedWith(processor)
                .compilesWithoutError();

    }

    @Test
    public void shouldCompileMetaAnnotationJavaWithoutErrors() {
        final RadesBuilderProcessor processor = new RadesBuilderProcessor();
        processor.setJavaModelService(new DefaultJavaModelProvider());

        assertAbout(javaSource())
                .that(JavaFileObjects.forResource(urlMetaAnnotationJava))
                .processedWith(processor)
                .compilesWithoutError();

    }


    @Test
    public void shouldCompileNonPackageClassWithoutErrors() {
        final RadesBuilderProcessor processor = new RadesBuilderProcessor();
        processor.setJavaModelService(new DefaultJavaModelProvider());

        assertAbout(javaSource())
                .that(JavaFileObjects.forSourceString("NonePackageClass", "\n" +
                        "@com.github.funthomas424242.rades.annotations.builder.RadesAddBuilder\n" +
                        "public class NonePackageClass {\n" +
                        "}\n"))
                .processedWith(processor)
                .compilesWithoutError();

    }


    @Test
    public void shouldFailToWriteNoneWriteableBuilderClass() {

        final RadesBuilderProcessor processor = new RadesBuilderProcessor();
        processor.setJavaModelService(new NoWritableJavaModelProvider());

        expectFailure.whenTesting()
                .about(javaSource())
                .that(JavaFileObjects.forSourceString("com.github.funthomas424242.domain.NoneWriteable", "\n" +
                        "package com.github.funthomas424242.domain;\n" +
                        "@com.github.funthomas424242.rades.annotations.builder.RadesAddBuilder\n" +
                        "public class NoneWriteable {\n" +
                        "}\n"))
                .processedWith(processor)
                .compilesWithoutError()
                .and()
                .generatesFiles(JavaFileObjects.forSourceString(
                        "com.github.funthomas424242.domain.NoneWriteableBuilder", NONE_WRITEABLE_BUILDER_JAVA));

        final AssertionError expected = expectFailure.getFailure();
        assertThat(expected.getMessage()).contains("Did not find a generated file corresponding to com/github/funthomas424242/domain/NoneWriteableBuilder.java");
    }


    @Test(expected = NullPointerException.class)
    public void shouldFailToCloseNoneCloseableBuilderClass() {

        final RadesBuilderProcessor processor = new RadesBuilderProcessor();
        processor.setJavaModelService(new NoClosebleJavaModelProvider());

        assertAbout(javaSource())
                .that(JavaFileObjects.forResource(urlPersonJava))
                .processedWith(processor)
                .compilesWithoutError()
                .and()
                .generatesSources(JavaFileObjects.forResource(urlPersonBuilderJava));
    }

    @Test
    public void shouldCompileInterfaceKatzeWithoutErrors() {
        final RadesBuilderProcessor processor = new RadesBuilderProcessor();
        processor.setJavaModelService(new RadesBuilderProcessorTest.DefaultJavaModelProvider());

        expectFailure
                .whenTesting()
                .about(javaSource())
                .that(JavaFileObjects.forResource(urlKatzeJava))
                .processedWith(processor)
                .compilesWithoutError()
                .and()
                .generatesSources(JavaFileObjects.forResource(urlKatzeBuilderJava));

        final AssertionError expected = expectFailure.getFailure();
        assertThat(expected.getMessage())
                .contains("Compilation generated no additional source files, though some were expected.");

    }
}
