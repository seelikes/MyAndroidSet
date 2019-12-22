package com.example.myjetpackapplication.annotationprocessor.businessannotationprocessor.compiler;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.myjetpackapplication.annotationprocessor.businessannotationprocessor.annotation.Business;
import com.example.myjetpackapplication.annotationprocessor.businessannotationprocessor.annotation.BusinessItem;
import com.google.auto.service.AutoService;
import com.java.lib.oil.GlobalMethods;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * Created by liutiantian on 2019-12-21 18:17 星期六
 */
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({"com.example.myjetpackapplication.annotationprocessor.businessannotationprocessor.annotation.Business"})
@AutoService(Processor.class)
public class BusinessCompiler extends AbstractProcessor {
    public BusinessCompiler() {

    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "BusinessCompiler START!!");
        if (annotations == null || annotations.isEmpty()) {
            return false;
        }

        for (TypeElement element : annotations) {
            if (!GlobalMethods.getInstance().checkIn(element.getQualifiedName().toString(), Business.class.getCanonicalName())) {
                return false;
            }
        }

        Set<? extends Element> itemAnnotations = roundEnv.getElementsAnnotatedWith(Business.class);
        if (itemAnnotations == null || itemAnnotations.isEmpty()) {
            return true;
        }

        ClassName GlobalMethods = ClassName.bestGuess("com.java.lib.oil.GlobalMethods");
        ClassName List = ClassName.bestGuess("java.util.List");
        ClassName ArrayList = ClassName.bestGuess("java.util.ArrayList");
        ParameterizedTypeName ListBusinessItem = ParameterizedTypeName.get(List, ClassName.get(BusinessItem.class));

        FieldSpec.Builder businessesBuilder = FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(List.class), ClassName.get(BusinessItem.class)), "businesses", Modifier.PRIVATE, Modifier.STATIC);

        MethodSpec.Builder getChildrenBuilder = MethodSpec.methodBuilder("getChildren")
                .addJavadoc("DO NOT EDIT, AUTO GENERATE CODE!!\n")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(ClassName.get(String.class), "parent")
                .returns(ListBusinessItem)
                .addCode("if (businesses == null || businesses.isEmpty()) {\n")
                .addCode("    businesses = listAll();\n")
                .addCode("}\n")
                .addCode("if (businesses == null || businesses.isEmpty()) {\n")
                .addCode("    return null;\n")
                .addCode("}\n")
                .addCode("$T res = new $T<>();\n", ListBusinessItem, ArrayList)
                .addCode("for (int i = 0; i < businesses.size(); ++i) {\n")
                .addCode("    if ($T.getInstance().checkEqual(businesses.get(i).getParent(), parent)) {\n", GlobalMethods)
                .addCode("        res.add(businesses.get(i));\n")
                .addCode("    }\n")
                .addCode("}\n")
                .addCode("return res;\n");

        MethodSpec.Builder tryBackBuilder = MethodSpec.methodBuilder("tryBack")
                .addJavadoc("DO NOT EDIT, AUTO GENERATE CODE!!\n")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(ClassName.get(BusinessItem.class), "current")
                .addParameter(ListBusinessItem, "layer")
                .returns(ListBusinessItem)
                .addCode("if (businesses == null || businesses.isEmpty()) {\n")
                .addCode("    businesses = listAll();\n")
                .addCode("}\n")
                .addCode("if (businesses == null || businesses.isEmpty()) {\n")
                .addCode("    return null;\n")
                .addCode("}\n")
                .addCode("if (layer == null || layer.isEmpty()) {\n")
                .addCode("    layer = businesses;\n")
                .addCode("}\n")
                .addCode("for (int i = 0; i < layer.size(); ++i) {\n")
                .addCode("    $T children = getChildren(layer.get(i).getTitle());\n", ListBusinessItem)
                .addCode("    if (children == null || children.isEmpty()) {\n")
                .addCode("        continue;\n")
                .addCode("    }\n")
                .addCode("    for (int j = 0; j < children.size(); ++j) {\n")
                .addCode("        if ($T.getInstance().checkEqual(children.get(j).getTitle(), current)) {\n", GlobalMethods)
                .addCode("            return layer;\n")
                .addCode("        }\n")
                .addCode("        $T res = tryBack(current, children);\n", ListBusinessItem)
                .addCode("        if (res != null && !res.isEmpty()) {\n")
                .addCode("            return res;\n")
                .addCode("        }\n")
                .addCode("    }\n")
                .addCode("}\n")
                .addCode("return null;\n");

        MethodSpec.Builder listAllBuilder = MethodSpec.methodBuilder("listAll")
                .addJavadoc("DO NOT EDIT, AUTO GENERATE CODE!!\n")
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .returns(ListBusinessItem)
                .addCode("$T res = new $T<>();\n", ListBusinessItem, ArrayList);
        for (Element element : itemAnnotations) {
            Route route = element.getAnnotation(Route.class);
            if (route == null) {
                continue;
            }
            Business business = element.getAnnotation(Business.class);
            listAllBuilder.addCode("{\n");
            listAllBuilder.addCode("    $T business = new $T();\n", BusinessItem.class, BusinessItem.class);
            listAllBuilder.addCode("    business.setTitle($S);\n", business.title());
            if (!business.parent().isEmpty()) {
                listAllBuilder.addCode("    business.setParent($S);\n", business.parent());
            }
            listAllBuilder.addCode("    business.setPath($S);\n", route.path());
            listAllBuilder.addCode("    business.setPriority($L);\n", business.priority());
            listAllBuilder.addCode("    business.setEnable($L);\n", business.enable());
            listAllBuilder.addCode("    res.add(business);\n");
            listAllBuilder.addCode("}\n");
        }
        listAllBuilder.addCode("return res;\n");

        TypeSpec BusinessManagerClass = TypeSpec.classBuilder("BusinessManager")
                .addJavadoc("PLEASE DO NOT EDIT THIS CLASS, IT IS AUTO GENERATED, REFRESH FROM BUILD TO BUILD!\n")
                .addModifiers(Modifier.PUBLIC)
                .addField(businessesBuilder.build())
                .addMethod(listAllBuilder.build())
                .addMethod(getChildrenBuilder.build())
                .addMethod(tryBackBuilder.build())
                .build();

        String packageName = null;
        for (Element element : itemAnnotations) {
            if (packageName == null || packageName.isEmpty()) {
                packageName = element.getEnclosingElement().toString();
            }
            else {
                String newPackageName = element.getEnclosingElement().toString();
                if (packageName.length() > newPackageName.length()) {
                    packageName = newPackageName;
                }
            }
        }
        try {
            JavaFile.builder(packageName, BusinessManagerClass).indent("    ").build().writeTo(processingEnv.getFiler());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
