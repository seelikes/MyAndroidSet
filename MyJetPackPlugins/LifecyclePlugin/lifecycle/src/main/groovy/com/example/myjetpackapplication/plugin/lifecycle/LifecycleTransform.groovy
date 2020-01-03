package com.example.myjetpackapplication.plugin.lifecycle

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import javassist.ClassPool
import org.apache.commons.codec.digest.DigestUtils
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
        project.logger.info("Log class imported to environment")
        transformInvocation.inputs.each { input ->
            input.directoryInputs.each { directoryInput ->
                ClassPool.default.appendClassPath(directoryInput.file.absolutePath)
                directoryInput.file.eachFileRecurse { file ->
                    if (!file.name.endsWith(".class") || (file.name.matches(/^R\$[a-zA-Z]+?\.class$/) || file.name == "R.class")) {
                        return
                    }
                    project.logger.info("file name " + file.name + " is ok to be transformed")
                    def packagePath = file.absolutePath.replaceAll("\\\\", ".")
                    def canonicalName = packagePath.substring(packagePath.indexOf(extension.packageName), packagePath.length() - 6)
                    def ctClass = ClassPool.default.getCtClass(canonicalName)
                    if (ctClass.isFrozen()) {
                        ctClass.defrost()
                    }
                    ctClass.declaredMethods.each { declaredMethod ->
                        project.logger.info("declaredMethod name " + declaredMethod.name + "; declaredMethod.getAnnotation(Override.class) != null: " + (declaredMethod.getAnnotation(Override.class) != null))
                        if (declaredMethod.name.startsWith("on")) {
                            project.logger.info("declaredMethod.annotations.length: " + declaredMethod.annotations.length)
                            project.logger.info(declaredMethod.name + " transform enter")
                            declaredMethod.insertBefore(String.format("android.util.Log.i(\"%s\", \"%s, %s, enter\");", file.name.substring(0, file.name.length() - 6), extension.tag, declaredMethod.name))
//                            declaredMethod.insertBefore("Log.i(" + file.name.substring(0, file.name.length() - 6) + ", " + extension.tag + ", " + declaredMethod.name + ", leave;")
                        }
                    }
                    ctClass.writeFile directoryInput.file.absolutePath
                    ctClass.detach()
                }
                def dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, dest)
            }

            input.jarInputs.each { jarInput ->
                def jarName = jarInput.name
                println("jar = " + jarInput.file.getAbsolutePath())
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                def dest = transformInvocation.outputProvider.getContentLocation(jarName + md5Name, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                FileUtils.copyFile(jarInput.file, dest)
            }
        }
        project.logger.info("transform door close")
    }
}
