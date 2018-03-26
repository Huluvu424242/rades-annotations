package com.github.funthomas424242.rades.annotations.processors;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
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

                final Name className = typeElement.getQualifiedName();
                try {
                    writeBuilderFile(className, mapName2Type);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    protected String getFullQualifiedClassName(final TypeMirror typeMirror) {
//        String typeName=null;
//        if(typeMirror instanceof DeclaredType){
//            final DeclaredType type = (DeclaredType) typeMirror;
//        }
        return typeMirror.toString();
    }


    private void writeBuilderFile(final Name typeName, Map<Name, TypeMirror> mapFieldName2Type)
            throws IOException {

        final String className = typeName.toString();

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

            if (packageName != null) {
                out.print("package ");
                out.print(packageName);
                out.println(";");
                out.println();
            }

            out.print("public class ");
            out.print(builderSimpleClassName);
            out.println(" {");
            out.println();

            out.print("    private ");
            out.print(simpleClassName);
            out.print(" " + objectName + " = new ");
            out.print(simpleClassName);
            out.println("();");
            out.println();

            out.print("    public ");
            out.print(simpleClassName);
            out.println(" build() {");
            out.println("        final " + simpleClassName + " value = this." + objectName + ";");
            out.println("        this." + objectName + " = null;");
            out.println("        return value;");
            out.println("    }");
            out.println();

            mapFieldName2Type.entrySet().forEach(fields -> {
                final String fieldName = fields.getKey().toString();
                final String setterName = "with" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                final String argumentType = fields.getValue().toString();

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

}
