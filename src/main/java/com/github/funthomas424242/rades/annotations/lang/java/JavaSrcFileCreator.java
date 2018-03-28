package com.github.funthomas424242.rades.annotations.lang.java;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JavaSrcFileCreator implements AutoCloseable {

    protected final PrintWriter writer;

    public JavaSrcFileCreator(final Filer filer, final String className) throws IOException {
        this.writer = new PrintWriter(filer.createSourceFile(className).openWriter());
    }

    public JavaSrcFileCreator(final Filer filer, final Element packageElement, final CharSequence className) throws IOException {
        this.writer = new PrintWriter(filer.createSourceFile(className, packageElement).openWriter());
    }


    public void writeSetterMethod(String objectName, String builderSimpleClassName, String fieldName, String setterName, String argumentType) {
        writer.print("    public ");
        writer.print(builderSimpleClassName);
        writer.print(" ");
        writer.print(setterName);

        writer.print("( final ");

        writer.print(argumentType);
        writer.println(" " + fieldName + " ) {");
        writer.print("        this." + objectName + ".");
        writer.print(fieldName);
        writer.println(" = " + fieldName + ";");
        writer.println("        return this;");
        writer.println("    }");
        writer.println();
    }

    public void writeConstructors(String simpleClassName, String objectName, String builderSimpleClassName) {
        writer.print("    public " + builderSimpleClassName + "(){\n");
        writer.print("        this(new " + simpleClassName + "());\n");
        writer.print("    }\n");
        writer.print("\n");
        writer.print("    public " + builderSimpleClassName + "( final " + simpleClassName + " " + objectName + " ){\n");
        writer.print("        this." + objectName + " = " + objectName + ";\n");
        writer.print("    }\n");
        writer.println();
    }

    public void writeFieldDefinition(String simpleClassName, String objectName) {
        writer.print("    private ");
        writer.print(simpleClassName);
        writer.print(" " + objectName + ";\n\n");
    }

    public void writeClassDeclaration(String builderSimpleClassName) {
        writer.println("public class " + builderSimpleClassName + " {");
        writer.println();
    }

    public String getNowAsISOString() {
        final LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public void writeBuildMethod(String simpleClassName, String objectName) {
        writer.print("    public ");
        writer.print(simpleClassName);
        writer.println(" build() {");
        writer.println("        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();");
        writer.println("        final java.util.Set<ConstraintViolation<" + simpleClassName + ">> constraintViolations = validator.validate(this." + objectName + ");");
        writer.println();
        writer.println("        if (constraintViolations.size() > 0) {");
        writer.println("            java.util.Set<String> violationMessages = new java.util.HashSet<String>();");
        writer.println();
        writer.println("            for (ConstraintViolation<?> constraintViolation : constraintViolations) {");
        writer.println("                violationMessages.add(constraintViolation.getPropertyPath() + \": \" + constraintViolation.getMessage());");
        writer.println("            }");
        writer.println();
        writer.println("            throw new ValidationException(\"" + simpleClassName + " is not valid:\\n\" + StringUtils.join(violationMessages, \"\\n\"));");
        writer.println("        }");
        writer.println("        final " + simpleClassName + " value = this." + objectName + ";");
        writer.println("        this." + objectName + " = null;");
        writer.println("        return value;");
        writer.println("    }");
        writer.println();
    }

    public void writeClassAnnotations(String className) {
        writer.print("@Generated(value=\"com.github.funthomas424242.rades.annotations.processors.RadesBuilderProcessor\"\n" +
                //TODO Zeiterzeugung in Utilklasse auslagern und im Test mocken
                //", date=\"" + nowString + "\"\n" +
                ", comments=\"" + className + "\")\n"
        );
    }

    public void writePackage(final String packageName) {
        writer.print("package ");
        writer.println(packageName + ";");
    }

    public void writeImports() {
        writer.println("import javax.annotation.Generated;");
        writer.println("import org.apache.commons.lang3.StringUtils;\n");
        writer.println("import javax.validation.ConstraintViolation;");
        writer.println("import javax.validation.Validation;");
        writer.println("import javax.validation.ValidationException;");
        writer.println("import javax.validation.Validator;");
        writer.println();
    }

    public void writeClassFinal() {
        writer.println("}");
    }

    @Override
    public void close() throws Exception {
        writer.close();
    }
}

