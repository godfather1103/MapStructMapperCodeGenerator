package io.github.godfather1103;

import io.github.godfather1103.annotations.EntityMapping;
import io.github.godfather1103.annotations.MapperCodeGenerator;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Title:        Godfather1103's Github</p>
 * <p>Copyright:    Copyright (c) 2023</p>
 * <p>Company:      https://github.com/godfather1103</p>
 * CodeGeneratorProcessor
 *
 * @author 作者: Jack Chu E-mail: chuchuanbao@gmail.com
 * @version 1.0
 * @date 创建时间：2023/9/21 19:05
 * @since 1.0
 */
public class CodeGeneratorProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(MapperCodeGenerator.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                if (!element.getKind().isClass()) {
                    continue;
                }
                MapperCodeGenerator generatorAnnotation = element.getAnnotation(MapperCodeGenerator.class);
                String msg = String.format(
                        "注解文件[%s]",
                        element.getSimpleName()
                );
                try {
                    printLog(Diagnostic.Kind.NOTE, "开始" + msg);
                    generatorCode(annotation, generatorAnnotation, element);
                    printLog(Diagnostic.Kind.NOTE, "完成" + msg);
                } catch (IOException e) {
                    printLog(Diagnostic.Kind.ERROR, "创建Mapper出错，" + e.getMessage());
                }
            }
        }
        return true;
    }

    private void printLog(Diagnostic.Kind kind, String msg) {
        this.processingEnv.getMessager().printMessage(kind, msg);
    }

    private void generatorCode(TypeElement annotation, MapperCodeGenerator generatorAnnotation, Element element) throws IOException {
        String sourceClass = element.toString();
        String className = !generatorAnnotation.mapperClassName().isEmpty() ? generatorAnnotation.mapperClassName() : element.getSimpleName() + "EntityConvert";
        String packageName = generatorAnnotation.mapperPackageName().isEmpty() ? sourceClass.substring(0, sourceClass.lastIndexOf(".")) : generatorAnnotation.mapperPackageName();
        JavaFileObject file = this.processingEnv.getFiler().createSourceFile(
                packageName
                        + "."
                        + className
        );
        Writer writer = file.openWriter();
        String classFile = "package " + packageName + ";\n"
                + "import org.mapstruct.factory.Mappers;\n"
                + "import org.mapstruct.*;\n"
                + "@Mapper(componentModel=\"" + generatorAnnotation.componentModel() + "\")\n"
                + "public interface " + className + " {\n"
                + className + " INSTANCE = Mappers.getMapper(" + className + ".class);\n"
                + String.join("\n", makeConvertMethods(annotation, element))
                + "\n}";
        writer.write(classFile);
        writer.flush();
        writer.close();
    }

    private List<String> makeConvertMethods(TypeElement annotation, Element element) {
        List<String> methodList = new ArrayList<>(0);
        String sourceEntityClassName = element.toString();
        for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            if (EntityMapping.class.getName().equals(annotationMirror.getAnnotationType().toString())) {
                String method = makeMethodCode(annotationMirror, sourceEntityClassName);
                if (!method.isEmpty()) {
                    methodList.add(method);
                }
            }
        }
        return methodList;
    }

    private String makeMethodCode(AnnotationMirror annotationMirror, String sourceEntityClassName) {
        StringBuilder methodBody = new StringBuilder();
        Map<? extends ExecutableElement, ? extends AnnotationValue> map = annotationMirror.getElementValues();
        String targetEntityClass = "";
        String methodName = "";
        String mappings = "";
        for (ExecutableElement key : map.keySet()) {
            AnnotationValue value = map.get(key);
            printLog(Diagnostic.Kind.NOTE, "key=" + key);
            printLog(Diagnostic.Kind.NOTE, "value=" + value.getValue().toString());
            switch (key.toString()) {
                case "targetEntityClass()":
                    targetEntityClass = value.getValue().toString();
                    break;
                case "methodName()":
                    methodName = value.getValue().toString();
                    break;
                case "mappings()":
                    List<String> obj = (List<String>) ((List) value.getValue())
                            .stream()
                            .map(Object::toString)
                            .collect(Collectors.toList());
                    mappings = String.join("\n", obj);
                    break;
                default:
                    break;
            }
        }
        String targetEntityClassName = targetEntityClass.substring(targetEntityClass.lastIndexOf(".") + 1);
        if (methodName.isEmpty()) {
            methodName = "convertTo" + targetEntityClassName;
        }
        if (!mappings.isEmpty()) {
            methodBody.append(mappings).append("\n");
        }
        methodBody.append(targetEntityClass)
                .append(" ")
                .append(methodName)
                .append("(")
                .append(sourceEntityClassName)
                .append(" source);");
        return methodBody.toString();
    }
}
