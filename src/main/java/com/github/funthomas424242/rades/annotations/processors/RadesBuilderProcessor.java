package com.github.funthomas424242.rades.annotations.processors;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SupportedAnnotationTypes("com.github.funthomas424242.rades.annotations.RadesBuilder")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class RadesBuilderProcessor extends AbstractProcessor {

    protected ProcessingEnvironment processingEnvironment;

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.processingEnvironment = processingEnv;
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
//        final Types types = this.processingEnvironment.getTypeUtils();

        for (TypeElement annotation : annotations) {
            final Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            for (final Element annotatedElement : annotatedElements) {
                // TODO compute ANNOTATION_TYPE additional to CLASS
                if (!annotatedElement.getKind().isClass()) {
                    continue;
                }
                final TypeElement typeElement = (TypeElement) annotatedElement;
                final Map<Name, TypeMirror> mapName2Type = new HashMap<>();
                final List<? extends Element> classMembers = annotatedElement.getEnclosedElements();
                for (final Element classMember : classMembers) {
                    if (classMember.getKind().isField()) {
                        final Set<Modifier> fieldModifiers = classMember.getModifiers();
                        if (fieldModifiers.contains(Modifier.PUBLIC) || fieldModifiers.contains(Modifier.PROTECTED)) {
                            final Name fieldName = classMember.getSimpleName();
                            final TypeMirror fieldTypeMirror = classMember.asType();
                            mapName2Type.put(fieldName, fieldTypeMirror);
                        }
                    }
                }

                try {
                    writeBuilderFile(typeElement, mapName2Type);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    protected String getFullQualifiedClassName(final TypeMirror typeMirror) {
        final String typeName;
        if (typeMirror instanceof DeclaredType) {
            final DeclaredType type = (DeclaredType) typeMirror;
            typeName = type.asElement().toString();
        } else {
            typeName = typeMirror.toString();
        }
        return typeName;
    }


    private void writeBuilderFile(final TypeElement typeElement, Map<Name, TypeMirror> mapFieldName2Type)
            throws IOException {

        final String className = typeElement.getQualifiedName().toString();

        String packageName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }

        String simpleClassName = className.substring(lastDot + 1);
        final String objectName = simpleClassName.substring(0, 1).toLowerCase() + simpleClassName.substring(1);
        String builderClassName = className + "Builder";
        String builderSimpleClassName = builderClassName
                .substring(lastDot + 1);

        JavaFileObject builderFile = processingEnv.getFiler()
                .createSourceFile(builderClassName);

        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
            getNowAsISOString();

            if (packageName != null) {
                writePackage(out,packageName);
                writeImports(out);
            }

            writeClassAnnotations(out, className);
            out.print(builderSimpleClassName);
            out.println(" {");
            out.println();

            out.print("    private ");
            out.print(simpleClassName);
            out.print(" " + objectName + ";\n\n" +
                    "    public " + builderSimpleClassName + "(){\n" +
                    "        this(new " + simpleClassName + "());\n" +
                    "    }\n" +
                    "\n" +
                    "    public " + builderSimpleClassName + "( final " + simpleClassName + " " + objectName + " ){\n" +
                    "        this." + objectName + " = " + objectName + ";\n" +
                    "    }\n");
            out.println();

            out.print("    public ");
            out.print(simpleClassName);
            writeBuildMethod(out, simpleClassName, objectName);

            mapFieldName2Type.entrySet().forEach(fields -> {
                final String fieldName = fields.getKey().toString();
                final String setterName = "with" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                final String argumentType = getFullQualifiedClassName(fields.getValue());

                out.print("    public ");
                out.print(builderSimpleClassName);
                out.print(" ");
                out.print(setterName);

                out.print("( final ");

                out.print(argumentType);
                out.println(" " + fieldName + " ) {");
                out.print("        this." + objectName + ".");
                out.print(fieldName);
                out.println(" = " + fieldName + ";");
                out.println("        return this;");
                out.println("    }");
                out.println();
            });

            out.println("}");
        }
    }

    private String getNowAsISOString() {
        final LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    private void writeBuildMethod(PrintWriter out, String simpleClassName, String objectName) {
        out.println(" build() {");
        out.println("        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();");
        out.println("        final java.util.Set<ConstraintViolation<" + simpleClassName + ">> constraintViolations = validator.validate(this." + objectName + ");");
        out.println();
        out.println("        if (constraintViolations.size() > 0) {");
        out.println("            java.util.Set<String> violationMessages = new java.util.HashSet<String>();");
        out.println();
        out.println("            for (ConstraintViolation<?> constraintViolation : constraintViolations) {");
        out.println("                violationMessages.add(constraintViolation.getPropertyPath() + \": \" + constraintViolation.getMessage());");
        out.println("            }");
        out.println();
        out.println("            throw new ValidationException(\"" + simpleClassName + " is not valid:\\n\" + StringUtils.join(violationMessages, \"\\n\"));");
        out.println("        }");
        out.println("        final " + simpleClassName + " value = this." + objectName + ";");
        out.println("        this." + objectName + " = null;");
        out.println("        return value;");
        out.println("    }");
        out.println();
    }

    private void writeClassAnnotations(PrintWriter out, String className) {
        out.print("@Generated(value=\"com.github.funthomas424242.rades.annotations.processors.RadesBuilderProcessor\"\n" +
                //TODO Zeiterzeugung in Utilklasse auslagern und im Test mocken
                //", date=\"" + nowString + "\"\n" +
                ", comments=\"" + className + "\")\n" +
                "public class ");
    }

    private void writePackage(final PrintWriter out,final String packageName) {
        out.print("package ");
        out.println(packageName+";");
    }

    private void writeImports(final PrintWriter out) {
        out.println("import javax.annotation.Generated;");
        out.println("import org.apache.commons.lang3.StringUtils;\n");
        out.println("import javax.validation.ConstraintViolation;");
        out.println("import javax.validation.Validation;");
        out.println("import javax.validation.ValidationException;");
        out.println("import javax.validation.Validator;");
        out.println();
    }

}
