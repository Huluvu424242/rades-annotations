package com.github.funthomas424242.rades.annotations.builder.model.java;

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

import javax.annotation.processing.Filer;
import java.io.IOException;
import java.io.PrintWriter;

public class BuilderSrcFileCreator implements AutoCloseable {

    protected final BuilderInjectionService javaModelService;

    protected final Filer filer;

    protected final String className;

    protected PrintWriter writer;

    public BuilderSrcFileCreator(final Filer filer, final String className, final BuilderInjectionService javaModelService) {
        this.filer = filer;
        this.className = className;
        this.javaModelService=javaModelService;

    }

    public void init() throws IOException {
        this.writer = new PrintWriter(filer.createSourceFile(className).openWriter());
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
        writer.println("    public <A> A build(Class<A> accessorClass) {\n" +
                "        final "+simpleClassName+" "+objectName+" = this.build();\n" +
                "        this."+objectName+"="+objectName+";\n" +
                "        try{\n" +
                "            final Constructor<A> constructor=accessorClass.getDeclaredConstructor("+simpleClassName+".class);\n" +
                "            final A accessor = constructor.newInstance("+objectName+");\n" +
                "            this."+objectName+"=null;\n" +
                "            return accessor;\n" +
                "        }catch(NoSuchMethodException | IllegalAccessException|  InstantiationException|  InvocationTargetException ex){\n" +
                "            throw new InvalidAccessorException(\"ungültige Accessorklasse übergeben\",ex);\n" +
                "        }\n" +
                "    }\n");
    }

    public void writeClassAnnotations(String className) {
        writer.print("@Generated(value=\"RadesBuilderProcessor\"\n" +
                ", date=\"" + javaModelService.getNowAsISOString() + "\"\n" +
                ", comments=\"" + className + "\")\n"
        );
    }

    public void writePackage(final String packageName) {
        writer.print("package ");
        writer.println(packageName + ";");
    }

    public void writeImports() {
        writer.println("import com.github.funthomas424242.rades.annotations.accessors.InvalidAccessorException;");
        writer.println("import javax.annotation.Generated;");
        writer.println("import org.apache.commons.lang3.StringUtils;\n");
        writer.println("import javax.validation.ConstraintViolation;");
        writer.println("import javax.validation.Validation;");
        writer.println("import javax.validation.ValidationException;");
        writer.println("import javax.validation.Validator;");
        writer.println("import java.lang.reflect.Constructor;");
        writer.println("import java.lang.reflect.InvocationTargetException;");

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

