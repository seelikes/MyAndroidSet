package com.example.myjetpackapplication.plugin.lifecycle

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import javassist.*
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
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        project.logger.info("transform door open")
        for (def classPath in project.android.bootClasspath) {
            ClassPool.default.appendClassPath classPath.toString()
        }

        project.logger.info("Log class imported to environment")

        transformInvocation.referencedInputs

        transformInvocation.inputs.each { input ->
            input.jarInputs.each { jarInput ->
                ClassPool.default.appendClassPath(jarInput.file.absolutePath)
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                def dest = transformInvocation.outputProvider.getContentLocation(jarName + md5Name, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                FileUtils.copyFile(jarInput.file, dest)
            }

            input.directoryInputs.each { directoryInput ->
                ClassPool.default.appendClassPath(directoryInput.file.absolutePath)
                directoryInput.file.eachFileRecurse { file ->
                    if (!file.name.endsWith(".class") || (file.name.matches(/^R\$[a-zA-Z]+?\.class$/) || file.name == "R.class")) {
                        return
                    }
                    project.logger.info("file name " + file.name + " is ok to be transformed")
                    def packagePath = file.absolutePath.replaceAll("\\\\", ".")
                    if (packagePath.indexOf(extension.packageName) == -1) {
                        return
                    }
                    def canonicalName = packagePath.substring(packagePath.indexOf(extension.packageName), packagePath.length() - 6)
                    def ctClass = ClassPool.default.getCtClass(canonicalName)
                    if (ctClass.isFrozen()) {
                        ctClass.defrost()
                    }
                    transformClass(ctClass, ctClass, new ArrayList<CtMethod>())
                    ctClass.writeFile directoryInput.file.absolutePath
                    ctClass.detach()
                }
                def dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
        }
        project.logger.info("transform door close")
    }

    void transformClass(CtClass enterClass, CtClass ctClass, ArrayList<CtMethod> readonlyMethods) {
        if (ctClass == null || ctClass == ClassPool.default.get("java.lang.Object")) {
            return
        }
        ctClass.declaredMethods.each { declaredMethod ->
            project.logger.info(declaredMethod.name + " judge transform enter")
            if ((declaredMethod.modifiers & Modifier.FINAL) != 0 || (declaredMethod.modifiers & Modifier.STATIC) != 0) {
                readonlyMethods.add(declaredMethod)
                return
            }
            if (!readonlyMethods.isEmpty()) {
                def readonly = false
                for (def method in readonlyMethods) {
                    if (method.name == declaredMethod.name) {
                        if (method.parameterTypes.length == declaredMethod.parameterTypes.length) {
                            if (method.parameterTypes.length > 0) {
                                for (def i in 0 .. method.parameterTypes.length - 1) {
                                    if (method.parameterTypes[i] != declaredMethod.parameterTypes[i]) {
                                        break
                                    }
                                }
                            }
                            readonly = true
                            break
                        }
                    }
                }
                if (readonly) {
                    return
                }
            }
            if (declaredMethod.name.startsWith("on")) {
                project.logger.info(declaredMethod.name + " transform enter")
                if (ctClass == enterClass) {
                    declaredMethod.insertBefore(String.format("%s.i(\"%s\", \"%s, %s, enter\");", extension.loggerClass, enterClass.name, extension.tag, declaredMethod.name))
                    declaredMethod.insertAfter(String.format("%s.i(\"%s\", \"%s, %s, leave\");", extension.loggerClass, enterClass.name, extension.tag, declaredMethod.name))
                }
                else {
                    try {
                        enterClass.getDeclaredMethod(declaredMethod.name, declaredMethod.parameterTypes)
                    }
                    catch (NotFoundException ignored) {
                        try {
                            def newMethod = CtNewMethod.make(declaredMethod.returnType, declaredMethod.name, declaredMethod.parameterTypes, declaredMethod.exceptionTypes, null, enterClass)
                            enterClass.addMethod(newMethod)
                            newMethod.insertBefore(String.format("super.%s(\$\$);", declaredMethod.name))
                            newMethod.insertAfter(String.format("%s.i(\"%s\", \"%s, %s, enter and leave\");", extension.loggerClass, enterClass.name, extension.tag, declaredMethod.name))
                        }
                        catch (CannotCompileException cce) {
                            project.logger.debug("method can not compile when insert", cce)
                        }
                    }
                }
            }
        }
        transformClass(enterClass, ctClass.superclass, readonlyMethods)
        ctClass.interfaces.each { ctInterface ->
            transformClass(enterClass, ctInterface, readonlyMethods)
        }
    }
}
