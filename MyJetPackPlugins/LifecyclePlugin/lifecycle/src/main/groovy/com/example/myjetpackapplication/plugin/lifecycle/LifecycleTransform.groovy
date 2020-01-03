package com.example.myjetpackapplication.plugin.lifecycle

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import javassist.ClassPool
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

/**
 * Created by liutiantian on 2020-01-02 12:08 星期四
 */
class LifecycleTransform extends Transform {
    private Project project
    private LifecycleExtension extension

    LifecycleTransform(Project project, LifecycleExtension extension) {
        this.project = project
        this.extension = extension
    }

    @Override
    String getName() {
        return "Lifecycle"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return true
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        project.logger.info("transform door open")
        for (String classPath in project.android.bootClasspath) {
            ClassPool.default.appendClassPath classPath.toString()
        }
        ClassPool.default.importPackage("android.util.Log")
        transformInvocation.inputs.parallelStream().each { input ->
            input.directoryInputs.parallelStream().each { directoryInput ->
                ClassPool.default.appendClassPath(directoryInput.file.absolutePath)
                directoryInput.file.eachFileRecurse { file ->
                    if (!file.name.endsWith(".class") || (file.name.matches(/^R\$[a-zA-Z]+?\.class$/) || file.name == "R.class")) {
                        return
                    }
                    def packagePath = file.absolutePath.replaceAll("\\\\", ".")
                    def canonicalName = packagePath.substring(packagePath.indexOf(extension.packageName), packagePath.length() - 6)
                    def ctClass = ClassPool.default.getCtClass(canonicalName)
                    if (ctClass.isFrozen()) {
                        ctClass.defrost()
                    }
                    ctClass.declaredMethods.each { declaredMethod ->
                        if (declaredMethod.name.startsWith("on") && declaredMethod.declaringClass != ctClass) {
                            println declaredMethod.name + " transform enter"
                            declaredMethod.insertBefore("Log.i(" + file.name.substring(0, file.name.length() - 6) + ", " + extension.tag + ", " + declaredMethod.name + ", enter;")
//                            declaredMethod.insertBefore("Log.i(" + file.name.substring(0, file.name.length() - 6) + ", " + extension.tag + ", " + declaredMethod.name + ", leave;")
                        }
                    }
                }
                def dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
        }
        project.logger.info("transform door close")
    }
}
