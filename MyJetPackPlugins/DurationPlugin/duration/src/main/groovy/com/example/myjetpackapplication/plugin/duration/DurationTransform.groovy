package com.example.myjetpackapplication.plugin.duration

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import javassist.*
import javassist.expr.ExprEditor
import javassist.expr.MethodCall
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

/**
 * Created by liutiantian on 2020-01-29 04:35 星期三
 */
class DurationTransform extends Transform {
    private Project project
    private DurationExtension extension

    DurationTransform(Project project, DurationExtension extension) {
        this.project = project
        this.extension = extension
    }

    @Override
    String getName() {
        return "Duration"
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
        project.logger.info("Duration transform begin")

        for (def classPath in project.android.bootClasspath) {
            ClassPool.default.appendClassPath classPath.toString()
        }

        transformInvocation.inputs.each { input ->
            input.jarInputs.each { jarInput ->
                ClassPool.default.appendClassPath(jarInput.file.absolutePath)
            }

            input.directoryInputs.each { directoryInput ->
                ClassPool.default.appendClassPath(directoryInput.file.absolutePath)
            }
        }

        ClassPool.default.importPackage("com.github.seelikes.android.duration.api.DurationApi")

        transformInvocation.inputs.each { input ->
            input.jarInputs.each { jarInput ->
                injectJar(transformInvocation, jarInput)
            }

            input.directoryInputs.each { DirectoryInput directoryInput ->
                def fileClassMap = new HashMap()
                directoryInput.file.eachFileRecurse { File file ->
                    if (!file.name.endsWith(".class") || (file.name.matches(/^R\$[a-zA-Z]+?\.class$/) || file.name == "R.class")) {
                        return
                    }

                    def ctClass = getCtClassFromFile(file)
                    if (ctClass != null) {
                        if (ctClass.isFrozen()) {
                            ctClass.defrost()
                        }
                        injectClass(ctClass)

                        ctClass.writeFile new File(transformInvocation.context.temporaryDir, "classes").absolutePath
                        fileClassMap.put(ctClass, directoryInput.file)
                        ctClass.detach()
                    }
                }

                project.logger.info("!fileClassMap.isEmpty(): ${!fileClassMap.isEmpty()}")
                if (!fileClassMap.isEmpty()) {
                    fileClassMap.entrySet().each { Map.Entry<CtClass, File> entry ->
                        String relativePath = entry.key.name.replaceAll("\\.", "/") + ".class"
                        project.logger.info("relativePath: $relativePath")
                        boolean delete = new File(entry.value, relativePath).delete()
                        project.logger.info("entry.key.name: $entry.key.name; entry.value: ${entry.value}; delete: $delete")
                        project.logger.info("copy temp to real position")
                        FileUtils.copyFile(new File(transformInvocation.context.temporaryDir, "classes/" + relativePath), new File(entry.value, relativePath))
                    }
                }

                def dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
        }
    }

    CtClass getCtClassFromFile(File file) {
        if (file == null) {
            return null
        }
        if (!file.name.endsWith(".class")) {
            return null
        }
        if (extension.packageNames != null && extension.packageNames.length > 0) {
            def packagePath = file.absolutePath.replaceAll("\\\\", ".")
            for (String packageName : extension.packageNames) {
                if (packagePath.indexOf(packageName) == -1) {
                    return null
                }
                def canonicalName = packagePath.substring(packagePath.indexOf(packageName), packagePath.length() - 6)
                return ClassPool.default.getCtClass(canonicalName)
            }
        }
        def pathNames = file.absolutePath.split("/|\\\\")
        for (def i = pathNames.length; i > 0; --i) {
            if (pathNames[i - 1].matches("^\\d+\$")) {
                for (def j = i; j < pathNames.length; ++j) {
                    def classPath = pathNames[j..pathNames.length - 1].join("/")
                    project.logger.info("classPath: $classPath")
                    try {
                        return ClassPool.default.get(classPath.substring(0, classPath.length() - 6).replaceAll("/", '.'))
                    }
                    catch (NotFoundException ignored) {

                    }
                }
                break
            }
        }
        return null
    }

    boolean acceptMethod(CtMethod method) {
        if (extension.methodMatcher != null && !extension.methodMatcher.isEmpty()) {
            return extension.methodMatcher.matches(method.name)
        }
        return true
    }

    boolean acceptClass(CtClass ctClass) {
        if (extension.packageNames != null && extension.packageNames.length > 0) {
            for (String packageName : extension.packageNames) {
                if (ctClass.name.startsWith(packageName)) {
                    for (CtMethod method : ctClass.declaredMethods) {
                        if (acceptMethod(method)) {
                            return true
                        }
                    }
                }
            }
            return false
        }
        return true
    }

    boolean acceptJar(JarInput jarInput) {
        def jarFile = new JarFile(jarInput.file)
        try {
            def entries = jarFile.entries()
            while (entries.hasMoreElements()) {
                def entry = entries.nextElement()
                if (!entry.name.endsWith(".class")) {
                    continue
                }
                def canonicalName = entry.name.replaceAll("/", ".").substring(0, entry.name.length() - 6)
                def ctClass = ClassPool.default.getCtClass(canonicalName)
                if (acceptClass(ctClass)) {
                    return true
                }
            }
        }
        finally {
            jarFile.close()
        }
    }

    void injectMethod(CtMethod ctMethod) {
        project.logger.info("ctMethod.name: ${ctMethod.name}; acceptMethod(ctMethod): ${acceptMethod(ctMethod)}")
        if (acceptMethod(ctMethod)) {
            if (ctMethod.returnType != CtClass.voidType) {
                ctMethod.instrument(new ExprEditor() {
                    @Override
                    void edit(MethodCall m) throws CannotCompileException {
                        m.replace("{\n" +
                            "long startTime = System.currentTimeMillis();\n" +
                            "try {\n" +
                            "    \$_ = \$proceed(\$\$);\n" +
                            "}\n" +
                            "finally {\n" +
                            "    DurationApi.INSTANCE.report(\"\$class.getCanonicalName()\", \"${m.getMethodName()}\", startTime, System.currentTimeMillis());\n" +
                            "}\n" +
                        "}\n")
                    }
                })
            }
            else {
                ctMethod.instrument(new ExprEditor() {
                    @Override
                    void edit(MethodCall m) throws CannotCompileException {
                        m.replace("{\n" +
                            "long startTime = System.currentTimeMillis();\n" +
                            "" +
                            "try {\n" +
                            "    \$_ = \$proceed(\$\$);\n" +
                            "}\n" +
                            "finally {\n" +
                            "    DurationApi.INSTANCE.report(\"" + ctMethod.declaringClass.name + "\", \"$ctMethod.name\", startTime, System.currentTimeMillis());\n" +
                            "}\n" +
                        "}\n")
                    }
                })
            }
        }
    }

    void injectClass(CtClass ctClass, OutputStream outputStream = null) {
        project.logger.info("ctClass.name: ${ctClass.name}; ctClass.declaredMethods.length: ${ctClass.declaredMethods.length}")
        ctClass.declaredMethods.each { CtMethod declaredMethod ->
            injectMethod(declaredMethod)
        }
        if (outputStream != null) {
            outputStream.write(ctClass.toBytecode())
        }
    }

    void injectJar(TransformInvocation transformInvocation, JarInput jarInput) {
        if (acceptJar(jarInput)) {
            File tmpOutputJar = new File(transformInvocation.context.temporaryDir, jarInput.file.name)
            JarOutputStream tmpOutputJarStream = new JarOutputStream(new FileOutputStream(tmpOutputJar))
            JarFile jarFile = new JarFile(jarInput.file)
            try {
                def entries = jarFile.entries()
                while (entries.hasMoreElements()) {
                    def entry = entries.nextElement()
                    tmpOutputJarStream.putNextEntry(new ZipEntry(entry.name))
                    def canonicalName = entry.name.replaceAll("/", ".").substring(0, entry.name.length() - 6)
                    def ctClass = ClassPool.default.getCtClass(canonicalName)
                    if (acceptClass(ctClass)) {
                        injectClass(ctClass)
                    }
                    else {
                        def inputStream = jarFile.getInputStream(entry)
                        try {
                            def bytes = new byte[8192]
                            def bytesRead
                            while ((bytesRead = inputStream.read(bytes)) != -1) {
                                tmpOutputJarStream.write(bytes, 0, bytesRead as int)
                            }
                        }
                        finally {
                            inputStream.close()
                        }
                    }
                }
            }
            finally {
                tmpOutputJarStream.close()
                jarFile.close()
            }
            copyJar(transformInvocation, tmpOutputJar, jarInput.contentTypes, jarInput.scopes)
        }
        else {
            copyJar(transformInvocation, jarInput.file, jarInput.contentTypes, jarInput.scopes)
        }
    }

    void copyJar(TransformInvocation transformInvocation, File jarFile, Set<QualifiedContent.ContentType> contentTypes, Set<? super QualifiedContent.Scope> scopes) {
        def jarName = jarFile.name
        def md5Name = DigestUtils.md5Hex(jarFile.getAbsolutePath())
        if (jarName.endsWith(".jar")) {
            jarName = jarName.substring(0, jarName.length() - 4)
        }
        def dest = transformInvocation.outputProvider.getContentLocation(jarName + md5Name, contentTypes, scopes, Format.JAR)
        project.logger.info("dest: " + dest)
        FileUtils.copyFile(jarFile, dest)
    }
}
