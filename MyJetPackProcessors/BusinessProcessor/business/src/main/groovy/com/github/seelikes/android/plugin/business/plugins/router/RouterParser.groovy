package com.github.seelikes.android.plugin.business.plugins.router

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.TransformInvocation
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.github.seelikes.android.plugin.business.BusinessParser
import com.github.seelikes.android.plugin.business.utils.DirectoryCtClassForger
import com.github.seelikes.android.plugin.business.utils.JarCtClassForger
import javassist.CtClass
import javassist.CtConstructor
import javassist.CtField
import org.gradle.api.GradleException
import org.gradle.api.Project

/**
 * Created by liutiantian on 2020-11-16 21:34 星期一
 */
class RouterParser implements BusinessParser, JarCtClassForger, DirectoryCtClassForger {
    private RouterExtension extension
    private Map<Business, CtClass> businessCtClassMap = new HashMap<>()
    private CtClass apiClass
    private JarInput apiJarInput
    private DirectoryInput apiDirectoryInput
    private CtClass constantHolderClass
    private JarInput constantHolderJar
    private DirectoryInput constantHolderDirectory

    RouterParser(RouterExtension extension) {
        this.extension = extension
    }

    @Override
    void parse(Project project, TransformInvocation transformInvocation, DirectoryInput directoryInput, CtClass ctClass, File file) {
        project.logger.info("DirectoryInput.2216, ${System.currentTimeMillis()} ctClass: ${ctClass.name}")
        handleCtClass(project, ctClass)
        if (ctClass.name == extension.constantHolder) {
            constantHolderDirectory = directoryInput
            constantHolderClass = ctClass
        }
        if (extension.routerHolder != null && !extension.routerHolder.isEmpty() && ctClass.name == extension.routerHolder) {
            apiClass = ctClass
            apiDirectoryInput = directoryInput
        }
    }

    @Override
    void parse(Project project, TransformInvocation transformInvocation, JarInput jarInput, CtClass ctClass) {
        project.logger.info("JarInput.2216, ${System.currentTimeMillis()} ctClass.name: ${ctClass.name}")
        handleCtClass(project, ctClass)
        if (ctClass.name == extension.constantHolder) {
            constantHolderJar = jarInput
            constantHolderClass = ctClass
        }
        if (extension.routerHolder != null && !extension.routerHolder.isEmpty() && ctClass.name == extension.routerHolder) {
            apiClass = ctClass
            apiJarInput = jarInput
        } else if (ctClass.name == "com.example.myjetpackapplication.annotationprocessor.business.api.BusinessApi") {
            apiClass = ctClass
            apiJarInput = jarInput
        }
    }

    @Override
    boolean isIncremental() {
        return true
    }

    @Override
    void collect(Project project, TransformInvocation transformInvocation) {
        project.logger.info("RouterParser.collect.2216, enter, businessCtClassMap.isEmpty(): ${businessCtClassMap.isEmpty()}")
        if (businessCtClassMap.isEmpty()) {
            return
        }
        project.logger.info("RouterParser.collect.2216, apiClass != null: ${apiClass != null}")
        if (apiClass != null) {
            project.logger.info("RouterParser.collect.2216, apiJarInput != null: ${apiJarInput != null}")
            if (apiJarInput != null) {
                changeCtClassInJar(project, transformInvocation, apiJarInput) {
                    if (it != apiClass) {
                        return false
                    }
                    injectRouter(project, transformInvocation, it)
                    return true
                }
            } else if (apiDirectoryInput != null) {
                injectRouter(project, transformInvocation, apiClass)
                changeCtClassInDirectory(project, transformInvocation, apiDirectoryInput, apiClass)
            } else {
                throw new GradleException("no jar or directory contains api class")
            }
        }
        if (constantHolderClass != null) {
            if (constantHolderJar != null) {
                changeCtClassInJar(project, transformInvocation, constantHolderJar) {
                    if (it != constantHolderClass) {
                        return false
                    }
                    injectConstant(project, transformInvocation, it)
                    return true
                }
            } else if (constantHolderDirectory != null) {
                injectConstant(project, transformInvocation, constantHolderClass)
                changeCtClassInDirectory(project, transformInvocation, constantHolderDirectory, constantHolderClass)
            } else {
                throw new GradleException("no jar or directory contains constantHolder class")
            }
        }
    }

    private void handleCtClass(Project project, CtClass ctClass) {
        if (ctClass.hasAnnotation(Business.class)) {
            project.logger.info("DirectoryInput.2216.0000, ctClass: ${ctClass.name} got got hot")
            Object business = ctClass.getAnnotation(Business.class)
            project.logger.info("DirectoryInput.2216.0000, ctClass: ${ctClass.name}; business instanceof Business: ${business instanceof Business}")
            if (business instanceof Business) {
                businessCtClassMap.put(business, ctClass)
            }
        }
    }

    private void injectRouter(Project project, TransformInvocation transformInvocation, CtClass ctClass) {
        if (ctClass.isFrozen()) {
            ctClass.defrost()
        }
        CtConstructor classInitializer = ctClass.getClassInitializer()
        if (classInitializer == null) {
            classInitializer = ctClass.makeClassInitializer()
        }
        businessCtClassMap.each {
            Business business = it.key
            project.logger.info("RouterParser.collect.2216, business != null: ${business != null}")
            if (business == null) {
                return
            }
            project.logger.info("RouterParser.collect.2216, tweak enter")
            classInitializer.insertAfter(
                """
                    {
                        com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem item = new com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem();
                        item.setTitle(\"${business.title()}\");
                        item.setParent(\"${business.parent()}\");
                        item.setTargetClass(${it.value.name}.class);
                        item.setPath(\"${business.path()}\");
                        item.setPriority(${business.priority()});
                        item.setEnable(${business.enable()});
                        INSTANCE.addItem(item);
                    }
                """
            )
        }
    }

    private void injectConstant(Project project, TransformInvocation transformInvocation, CtClass ctClass) {
        if (ctClass.isFrozen()) {
            ctClass.defrost()
        }
        businessCtClassMap.each {
            Business business = it.key
            if (business == null) {
                return
            }
            String pathUpperCase = business.path().replaceAll("/", "_").toUpperCase()
            CtField.make("public static final String ${pathUpperCase} = \"${business.path()}\";", ctClass)
        }
    }
}
