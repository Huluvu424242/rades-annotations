package com.github.funthomas424242.rades.annotations.accessors.model.java;

import javax.annotation.processing.Filer;
import javax.lang.model.element.ExecutableElement;
import java.io.IOException;
import java.io.PrintWriter;

public class AccessorSrcFileCreator implements AutoCloseable {

    protected final AccessorInjectionService javaModelService;

    protected final Filer filer;

    protected final String className;

    protected PrintWriter writer;

    public AccessorSrcFileCreator(final Filer filer, final String className, final AccessorInjectionService javaModelService) {
        this.filer = filer;
        this.className = className;
        this.javaModelService = javaModelService;

    }

// Add if needed
//    public JavaSrcFileCreator(final Filer filer, final Element packageElement, final CharSequence className) throws IOException {
//        this.writer = new PrintWriter(filer.createSourceFile(className, packageElement).openWriter());
//    }


    public void init() throws IOException {
        this.writer = new PrintWriter(filer.createSourceFile(className).openWriter());
    }


    public void writeGetterMethod(final String objectName, final String fieldName, final String getterName, final String returnType) {
        writer.print("    public ");
        writer.print(returnType);
        writer.print(" ");
        writer.print(getterName);

        writer.println("( ) { ");
        writer.println("        return this." + objectName + "." + fieldName + ";");
        writer.println("    }");
        writer.println();
    }

    public void writeGenerateMethod(final String objectName, final ExecutableElement methode) {
        final String methodName = methode.getSimpleName().toString();
        final String returnType = methode.getReturnType().toString();
        writer.print("    public " + returnType + " " + methodName + "(");
        final int[] i = {1};
        methode.getParameters().forEach(parameter -> {
            if (i[0] != 1) {
                writer.append(", ");
            }
            i[0]++;
            final String parameterType = parameter.asType().toString();
            final String parameterName = parameter.getSimpleName().toString();
            writer.print("final " + parameterType + " " + parameterName);
        });
        writer.println("){");
        writer.print("        ");
        if (!"void".equals(returnType)) {
            writer.print("return ");
        }
        writer.print("this." + objectName + "." + methodName + "(");
        i[0] = 1;
        methode.getParameters().forEach(parameter -> {
            if (i[0] != 1) {
                writer.append(", ");
            }
            i[0]++;
            final String parameterName = parameter.getSimpleName().toString();
            writer.print(parameterName);
        });
        writer.println(");");
        writer.println("    }");
        writer.println();
    }

    public void writeGetOriginalObject(final String simpleClassName, final String objectName) {
        writer.println("    public " + simpleClassName + " to" + simpleClassName + "(){\n" +
                "        return this." + objectName + ";\n" +
                "    }\n");
    }

    public void writeConstructors(final String simpleClassName, final String objectName, final String accessorSimpleClassName) {
        writer.print("    public " + accessorSimpleClassName + "( final " + simpleClassName + " " + objectName + " ){\n");
        writer.print("        this." + objectName + " = " + objectName + ";\n");
        writer.print("    }\n");
        writer.println();
    }

    public void writeFieldDefinition(String simpleClassName, String objectName) {
        writer.print("    protected final ");
        writer.print(simpleClassName);
        writer.print(" " + objectName + ";\n\n");
    }

    public void writeClassDeclaration(final String accessorSimpleClassName) {
        writer.println("public class " + accessorSimpleClassName + " {");
        writer.println();
    }


    public void writeClassAnnotations(String className) {
        writer.print("@Generated(value=\"RadesAccessorProcessor\"\n" +
                ", date=\"" + javaModelService.getNowAsISOString() + "\"\n" +
                ", comments=\"" + className + "\")\n"
        );
    }

    public void writePackage(final String packageName) {
        writer.print("package ");
        writer.println(packageName + ";");
    }

    public void writeImports() {
        writer.println("import javax.annotation.Generated;");
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

