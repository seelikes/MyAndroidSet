package com.example.myjetpackapplication.annotationprocessor.business.compiler;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.myjetpackapplication.annotationprocessor.business.annotation.*;
import com.google.auto.service.AutoService;
import com.java.lib.oil.GlobalMethods;
import com.squareup.javapoet.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by liutiantian on 2019-12-21 18:17 星期六
 */
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({"com.example.myjetpackapplication.annotationprocessor.business.annotation.Businesses"})
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
            if (!GlobalMethods.getInstance().checkIn(element.getQualifiedName().toString(), Businesses.class.getCanonicalName())) {
                return false;
            }
        }

        Set<? extends Element> itemAnnotations = roundEnv.getElementsAnnotatedWith(Businesses.class);
        if (itemAnnotations == null || itemAnnotations.isEmpty()) {
            return true;
        }

        ClassName GlobalMethods = ClassName.bestGuess("com.java.lib.oil.GlobalMethods");
        ClassName Set = ClassName.bestGuess("java.util.Set");
        ClassName HashSet = ClassName.bestGuess("java.util.HashSet");
        ClassName List = ClassName.bestGuess("java.util.List");
        ClassName ArrayList = ClassName.bestGuess("java.util.ArrayList");
        ClassName BusinessItem = ClassName.bestGuess("com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem");
        ParameterizedTypeName ListBusinessItem = ParameterizedTypeName.get(List, BusinessItem);
        ParameterizedTypeName SetString = ParameterizedTypeName.get(Set, ClassName.get(String.class));

        FieldSpec.Builder businessesBuilder = FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(List.class), BusinessItem), "businesses", Modifier.PRIVATE, Modifier.STATIC);

        MethodSpec.Builder getChildrenBuilder = MethodSpec.methodBuilder("getChildren")
                .addJavadoc("DO NOT EDIT, AUTO GENERATE CODE!!\n")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(BusinessItem, "parent")
                .returns(ListBusinessItem)
                .addCode("if (businesses == null || businesses.isEmpty()) {\n")
                .addCode("    businesses = listAll();\n")
                .addCode("}\n")
                .addCode("if (businesses == null || businesses.isEmpty()) {\n")
                .addCode("    return null;\n")
                .addCode("}\n")
                .addCode("$T res = new $T<>();\n", ListBusinessItem, ArrayList)
                .addCode("for (int i = 0; i < businesses.size(); ++i) {\n")
                .addCode("    if ($T.getInstance().checkEqual(businesses.get(i).getParent(), parent != null && parent.getTitle() != null && !parent.getTitle().isEmpty() ? parent.getTitle() : null)) {\n", GlobalMethods)
                .addCode("        res.add(businesses.get(i));\n")
                .addCode("    }\n")
                .addCode("}\n")
                .addCode("if (parent == null && res.isEmpty()) {\n")
                .addCode("    $T parents = new $T<>();\n", SetString, HashSet)
                .addCode("    $T titles = new $T<>();\n", SetString, HashSet)
                .addCode("    for (int i = 0; i < businesses.size(); ++i) {\n")
                .addCode("        if (businesses.get(i).getParent() != null) {\n")
                .addCode("            parents.add(businesses.get(i).getParent());\n")
                .addCode("        }\n")
                .addCode("        if (businesses.get(i).getTitle() != null) {\n")
                .addCode("            titles.add(businesses.get(i).getTitle());\n")
                .addCode("        }\n")
                .addCode("    }\n")
                .addCode("    parents.removeAll(titles);\n")
                .addCode("    if (parents.size() == 1) {\n")
                .addCode("        $T business = new $T();\n", BusinessItem, BusinessItem)
                .addCode("        business.setTitle(parents.toArray(new $T[0])[0]);\n", String.class)
                .addCode("        return getChildren(business);\n")
                .addCode("    }\n")
                .addCode("}\n")
                .addCode("return res;\n");

        MethodSpec.Builder tryBackBuilder = MethodSpec.methodBuilder("tryBack")
                .addJavadoc("DO NOT EDIT, AUTO GENERATE CODE!!\n")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(BusinessItem, "current")
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
                .addCode("    $T children = getChildren(layer.get(i));\n", ListBusinessItem)
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
                .addAnnotation(ABusinessListAll.class)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ListBusinessItem)
                .addCode("$T res = new $T<>();\n", ListBusinessItem, ArrayList);
        for (Element element : itemAnnotations) {
            Businesses businesses = element.getAnnotation(Businesses.class);
            for (Business business : businesses.value()) {
                listAllBuilder.addCode("{\n");
                listAllBuilder.addCode("    $T business = new $T();\n", BusinessItem.class, BusinessItem.class);
                listAllBuilder.addCode("    business.setTitle($S);\n", business.title());
                if (!business.parent().isEmpty()) {
                    listAllBuilder.addCode("    business.setParent($S);\n", business.parent());
                }
                String path = business.path();
                if (!path.isEmpty()) {
                    listAllBuilder.addCode("    business.setPath($S);\n", path);
                }
                else {
                    Route route = element.getAnnotation(Route.class);
                    if (route != null) {
                        listAllBuilder.addCode("    business.setPath($S);\n", route.path());
                    }
                }
                listAllBuilder.addCode("    business.setPriority($L);\n", business.priority());
                listAllBuilder.addCode("    business.setEnable($L);\n", business.enable());
                listAllBuilder.addCode("    res.add(business);\n");
                listAllBuilder.addCode("}\n");
            }
        }
        listAllBuilder.addCode("return res;\n");

        TypeSpec BusinessManagerClass = TypeSpec.classBuilder("BusinessManager")
                .addJavadoc("PLEASE DO NOT EDIT THIS CLASS, IT IS AUTO GENERATED, REFRESH FROM BUILD TO BUILD!\n")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(ABusinessManager.class)
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
