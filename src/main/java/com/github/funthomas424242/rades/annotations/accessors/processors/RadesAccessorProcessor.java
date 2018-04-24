package com.github.funthomas424242.rades.annotations.accessors.processors;

import com.github.funthomas424242.rades.annotations.accessors.AddAccessor;
import com.github.funthomas424242.rades.annotations.accessors.RadesAddAccessor;
import com.github.funthomas424242.rades.annotations.accessors.model.java.AccessorInjectionService;
import com.github.funthomas424242.rades.annotations.accessors.model.java.AccessorInjectionServiceProvider;
import com.github.funthomas424242.rades.annotations.accessors.model.java.AccessorSrcFileCreator;
import com.github.funthomas424242.rades.annotations.lang.java.JavaModelHelper;
import com.google.auto.service.AutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

@SupportedAnnotationTypes({"com.github.funthomas424242.rades.annotations.accessors.RadesAddAccessor"
        , "com.github.funthomas424242.rades.annotations.accessors.AddAccessor"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class RadesAccessorProcessor extends AbstractProcessor {

    protected final Logger logger = LoggerFactory.getLogger(RadesAccessorProcessor.class);


    protected AccessorInjectionService javaModelService = new AccessorInjectionServiceProvider();

    protected ProcessingEnvironment processingEnvironment;

    /**
     * Please only use this method for mocking in your test code!
     *
     * @param javaModelService mock to replace the default intern instance.
     */
    public void setJavaModelService(final AccessorInjectionService javaModelService) {
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

        final Stack<TypeElement> allAnnotations = new Stack<>();
        annotations.stream().collect(Collectors.toList()).forEach(annotation -> {
            allAnnotations.push(annotation);
        });

        final Set<Element> processedAnnotations = new HashSet<>();
        final Set<Element> annotatedClasses = new HashSet<>();
        while (!allAnnotations.empty()) {
            final TypeElement annotation = allAnnotations.pop();
            processedAnnotations.add(annotation);

            final Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            for (final Element annotatedElement : annotatedElements) {
                if (annotatedElement.getKind() == ElementKind.ANNOTATION_TYPE) {
                    final TypeElement typeElement = (TypeElement) annotatedElement;
                    if (!processedAnnotations.contains(typeElement)) {
                        logger.debug("###Annotation: " + typeElement);
                        // als Annotation aufnehmen falls gerade nicht im Stack (Minioptimierung)
                        allAnnotations.push(typeElement);
                    }
                    continue;
                }
                if (annotatedElement.getKind().isClass()) {
                    logger.debug("###Class: " + annotatedElement);
                    annotatedClasses.add(annotatedElement);
                }
            }
        }

        annotatedClasses.forEach(element -> {
            createAccessorSrcFile(element);
        });

        return true;
    }

    private void createAccessorSrcFile(final Element annotatedElement) {
        logger.debug("###WRITE ACCESSOR for: " + annotatedElement);
        final TypeElement typeElement = (TypeElement) annotatedElement;
        final Map<Name, Element> mapName2Type = new HashMap<>();
        final List<? extends Element> classMembers = annotatedElement.getEnclosedElements();
        for (final Element classMember : classMembers) {
            if (classMember.getKind().isField() || classMember.getKind() == ElementKind.METHOD) {

                // Felder und Methoden in Map merken
                final Set<Modifier> fieldModifiers = classMember.getModifiers();
                if (!fieldModifiers.contains(Modifier.PRIVATE)) {
                    final Name fieldName = classMember.getSimpleName();
                    mapName2Type.put(fieldName, classMember);
                }

            }
        }

        writeAccessorFile(typeElement, mapName2Type);
    }

    protected void writeAccessorFile(final TypeElement typeElement, Map<Name, Element> mapFieldName2Type) {

        String specifiedAccessorClassName = null;
        specifiedAccessorClassName = getRadesAddAccessorSimpleClassName(typeElement, specifiedAccessorClassName);
        specifiedAccessorClassName = getAddAccessorSimpleClassName(typeElement, specifiedAccessorClassName);
        final String qualifiedClassName = typeElement.getQualifiedName().toString();
        final String simpleClassName = typeElement.getSimpleName().toString();
        final String packageName = JavaModelHelper.computePackageName(qualifiedClassName);

        final String newInstanceName = simpleClassName.substring(0, 1).toLowerCase() + simpleClassName.substring(1);
        final String accessorClassName = getAccessorClassName(specifiedAccessorClassName, packageName, qualifiedClassName);
        final String accessorSimpleClassName = getAccessorSimpleClassName(specifiedAccessorClassName, simpleClassName);
        logger.debug("###specifiedAccessorClassName: " + specifiedAccessorClassName);
        logger.debug("###accessorClassName: " + accessorClassName);
        logger.debug("###accessorSimpleClassName: " + accessorSimpleClassName);

        final Filer filer = processingEnv.getFiler();
        try (final AccessorSrcFileCreator javaSrcFileCreator = javaModelService.getJavaSrcFileCreator(filer, accessorClassName)) {

            javaSrcFileCreator.init();

            if (packageName != null) {
                javaSrcFileCreator.writePackage(packageName);
            }
            javaSrcFileCreator.writeImports();

            javaSrcFileCreator.writeClassAnnotations(qualifiedClassName);
            javaSrcFileCreator.writeClassDeclaration(accessorSimpleClassName);

            javaSrcFileCreator.writeFieldDefinition(simpleClassName, newInstanceName);

            javaSrcFileCreator.writeConstructors(simpleClassName, newInstanceName, accessorSimpleClassName);

            javaSrcFileCreator.writeGetOriginalObject(simpleClassName, newInstanceName);

            mapFieldName2Type.entrySet().forEach(entry -> {
                final Element element = entry.getValue();
                final TypeMirror memberType = element.asType();
                final String memberName = entry.getKey().toString();
                final String memberFullQualifiedTypName = getFullQualifiedTypeSignature(memberType);

                if (element.getKind().isField()) {
                    final String getterName = "get" + memberName.substring(0, 1).toUpperCase() + memberName.substring(1);
                    javaSrcFileCreator.writeGetterMethod(newInstanceName, memberName, getterName, memberFullQualifiedTypName);
                } else if (element.getKind() == ElementKind.METHOD) {
                    logger.debug("###Methode: " + memberName);
                    // TODO
                }
            });

            javaSrcFileCreator.writeToStringMethod(newInstanceName);
            javaSrcFileCreator.writeClassFinal();

        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    protected String getAccessorSimpleClassName(final String specifiedAccessorClassName, final String simpleClassName) {
        if (specifiedAccessorClassName != null) {
            return specifiedAccessorClassName;
        } else {
            return simpleClassName + "Accessor";
        }
    }

    protected String getAccessorClassName(final String specifiedAccessorClassName, final String packageName, final String className) {
        if (specifiedAccessorClassName != null) {
            return packageName + "." + specifiedAccessorClassName;
        } else {
            return className + "Accessor";
        }
    }

    protected String getRadesAddAccessorSimpleClassName(final TypeElement typeElement, final String specifiedAccessorClassName) {
        final RadesAddAccessor radesAddAccessor = typeElement.getAnnotation(RadesAddAccessor.class);
        if (specifiedAccessorClassName == null && radesAddAccessor != null) {
            final String tmp = radesAddAccessor.simpleAccessorClassName().trim();
            if (tmp.length() > 0) {
                return tmp;
            }
            logger.debug("###1|SimpleAccessorClassName: " + specifiedAccessorClassName);
        }
        return specifiedAccessorClassName;
    }

    protected String getAddAccessorSimpleClassName(final TypeElement typeElement, final String specifiedAccessorClassName) {
        final AddAccessor addAccessor = typeElement.getAnnotation(AddAccessor.class);
        if (specifiedAccessorClassName == null && addAccessor != null) {
            final String tmp = addAccessor.simpleAccessorClassName().trim();
            if (tmp.length() > 0) {
                return tmp;
            }
            logger.debug("###2|SimpleAccessorClassName: " + specifiedAccessorClassName);
        }
        return specifiedAccessorClassName;
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

    /**
     * Ermittelt die vollständige Typ Signatur - rekursiv!!!
     *
     * @param type TypeMirror des zu bestimmenden Datentypen
     * @return String Signatur des zu bestimmenden Datentypen
     */
    protected String getFullQualifiedTypeSignature(final TypeMirror type) {

        final StringBuffer typeSignature = new StringBuffer();

        if (type instanceof DeclaredType) {
            final DeclaredType declaredType = (DeclaredType) type;
            typeSignature.append(getFullQualifiedClassName(type));

            final List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
            if (!typeArguments.isEmpty()) {
                typeSignature.append("<");
                for (final ListIterator<? extends TypeMirror> it = typeArguments.listIterator(); it.hasNext(); ) {
                    typeSignature.append(getFullQualifiedTypeSignature((TypeMirror) it.next()));
                    if (it.hasNext()) {
                        typeSignature.append(",");
                    }
                }
                typeSignature.append(">");
            }
        } else {
            typeSignature.append(getFullQualifiedClassName(type));
        }

        return typeSignature.toString();
    }


}
