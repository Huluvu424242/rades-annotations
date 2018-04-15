package com.github.funthomas424242.rades.annotations.accessors.model.java;

import javax.annotation.processing.Filer;
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
        writer.println("        return this." +objectName +"."+ fieldName + ";");
        writer.println("    }");
        writer.println();
    }

    public void writeConstructors(final String simpleClassName, final String objectName, final String accessorSimpleClassName) {
        writer.print("    public " + accessorSimpleClassName + "( final " + simpleClassName + " " + objectName + " ){\n");
        writer.print("        this." + objectName + " = " + objectName + ";\n");
        writer.print("    }\n");
        writer.println();
    }

    public void writeFieldDefinition(String simpleClassName, String objectName) {
        writer.print("    private ");
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

