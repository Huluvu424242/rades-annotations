package com.github.funthomas424242.rades.annotations.processors;

import com.github.funthomas424242.rades.annotations.lang.java.JavaModelHelper;
import com.github.funthomas424242.rades.annotations.lang.java.JavaModelService;
import com.github.funthomas424242.rades.annotations.lang.java.JavaModelServiceProvider;
import com.github.funthomas424242.rades.annotations.lang.java.JavaSrcFileCreator;
import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SupportedAnnotationTypes("com.github.funthomas424242.rades.annotations.RadesBuilder")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class RadesBuilderProcessor extends AbstractProcessor {

    protected JavaModelService javaModelService = new JavaModelServiceProvider();

    protected ProcessingEnvironment processingEnvironment;

    /**
     * Please only use this method for mocking in your test code!
     *
     * @param javaModelService mock to replace the default intern instance.
     */
    protected void setJavaModelService(final JavaModelService javaModelService) {
        this.javaModelService = javaModelService;
    }

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

                writeBuilderFile(typeElement, mapName2Type);
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


    private void writeBuilderFile(final TypeElement typeElement, Map<Name, TypeMirror> mapFieldName2Type) {

        final String className = typeElement.getQualifiedName().toString();
        final String simpleClassName = typeElement.getSimpleName().toString();
        final String packageName = JavaModelHelper.computePackageName(className);

        final String newInstanceName = simpleClassName.substring(0, 1).toLowerCase() + simpleClassName.substring(1);
        final String builderClassName = className + "Builder";
        final String builderSimpleClassName = simpleClassName + "Builder";

        final Filer filer = processingEnv.getFiler();
        try (final JavaSrcFileCreator javaSrcFileCreator = javaModelService.getJavaSrcFileCreator(filer, builderClassName)) {

            javaSrcFileCreator.getNowAsISOString();

            if (packageName != null) {
                javaSrcFileCreator.writePackage(packageName);
            }
            javaSrcFileCreator.writeImports();

            javaSrcFileCreator.writeClassAnnotations(className);
            javaSrcFileCreator.writeClassDeclaration(builderSimpleClassName);

            javaSrcFileCreator.writeFieldDefinition(simpleClassName, newInstanceName);

            javaSrcFileCreator.writeConstructors(simpleClassName, newInstanceName, builderSimpleClassName);


            javaSrcFileCreator.writeBuildMethod(simpleClassName, newInstanceName);

            mapFieldName2Type.entrySet().forEach(fields -> {
                final String fieldName = fields.getKey().toString();
                final String setterName = "with" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                final String argumentType = getFullQualifiedClassName(fields.getValue());

                javaSrcFileCreator.writeSetterMethod(newInstanceName, builderSimpleClassName, fieldName, setterName, argumentType);
            });

            javaSrcFileCreator.writeClassFinal();
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }


}
