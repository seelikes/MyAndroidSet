package com.github.seelikes.android.plugin.connector

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.github.seelikes.android.plugin.connector.annotation.Connector
import javassist.ClassPool
import javassist.CtClass
import javassist.CtConstructor
import javassist.CtMethod
import javassist.NotFoundException
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.GradleException
import org.gradle.api.Project

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

/**
 * Created by liutiantian on 2020-06-21 14:34 星期日
 */
class ConnectorTransform extends Transform {
    private Project project
    private ConnectorExtension extension

    ConnectorTransform(Project project, ConnectorExtension extension) {
        this.project = project
        this.extension = extension
    }

    @Override
    String getName() {
        return "Connector"
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
        project.logger.info(name + " transform begin")

        for (def classPath in project.android.bootClasspath) {
            ClassPool.default.appendClassPath classPath.toString()
        }

        transformInvocation.inputs.each { input ->
            input.jarInputs.each { jarInput ->
                ClassPool.default.appendClassPath(jarInput.file.absolutePath)
            }

            input.directoryInputs.each { DirectoryInput directoryInput ->
                ClassPool.default.appendClassPath(directoryInput.file.absolutePath)
            }
        }

        JarInput apiJar

        List<ConnectorBean> beans = new ArrayList<>()
        transformInvocation.inputs.each { input ->
            input.jarInputs.each { JarInput jarInput ->
                if (parseJar(jarInput, beans)) {
                    apiJar = jarInput
                } else {
                    def jarName = jarInput.name
                    def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                    if (jarName.endsWith(".jar")) {
                        jarName = jarName.substring(0, jarName.length() - 4)
                    }
                    def dest = transformInvocation.outputProvider.getContentLocation(jarName + md5Name, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                    FileUtils.copyFile(jarInput.file, dest)
                }
            }

            input.directoryInputs.each { DirectoryInput directoryInput ->
                directoryInput.file.eachFileRecurse { File file ->
                    CtClass ctClass = getCtClassFromFile(file)
                    if (ctClass != null) {
                        parseClass(ctClass, beans)
                    }
                }
                FileUtils.copyDirectory(directoryInput.file, transformInvocation.outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY))
            }
        }

        if (apiJar == null) {
            project.logger.info(name + " transform end, no api jar found")
            return
        }

        if (beans.isEmpty()) {
            project.logger.info(name + " transform end, not usage found")
            return
        }

        JarFile jarFile = new JarFile(apiJar.file)
        project.logger.info(name + " apiJar.file: ${apiJar.file.absolutePath}")
        try {
            File tmpJar = new File(transformInvocation.context.temporaryDir, apiJar.file.name)
            JarOutputStream tmpJarOutputStream = new JarOutputStream(new FileOutputStream(tmpJar))
            try {
                Enumeration<JarEntry> entries = jarFile.entries()
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement()
                    project.logger.info(name + " entry.name: ${entry.name}")
                    if (entry.name == "com/github/seelikes/android/plugin/connector/api/ConnectorApi.class") {
                        CtClass ConnectorApi = ClassPool.default.get("com.github.seelikes.android.plugin.connector.api.ConnectorApi")
                        if (ConnectorApi.isFrozen()) {
                            ConnectorApi.detach()
                        }
                        CtConstructor staticInit = ConnectorApi.getClassInitializer()
                        StringBuilder staticInitBody = new StringBuilder()
                        beans.each { ConnectorBean bean ->
                            try {
                                if (bean.ctClass.isFrozen()) {
                                    bean.ctClass.defrost()
                                }
                                project.logger.info(name + " bean.connector: ${bean.connector}")
                                String interfaceClass = bean.getInterfaceClass()
                                if (interfaceClass == null || interfaceClass.isEmpty()) {
                                    return
                                }
                                staticInitBody.append("    {\n")
                                staticInitBody.append("        com.github.seelikes.android.plugin.connector.api.ConnectorBean bean = new com.github.seelikes.android.plugin.connector.api.ConnectorBean();\n")
                                staticInitBody.append("        bean.targetClass = ${bean.ctClass.name}.class;\n")
                                staticInitBody.append("        bean.singleton = ${bean.connector.singleton()};\n")
                                staticInitBody.append("        superClassConnectorMap.put(${interfaceClass}.class, bean);\n")

                                staticInitBody.append("    }\n")
                            }
                            finally {
                                bean.ctClass.detach()
                            }
                        }
                        staticInit.insertAfter(staticInitBody.toString())
                        ConnectorApi.detach()

                        tmpJarOutputStream.putNextEntry(new ZipEntry(entry.name))
                        tmpJarOutputStream.write(ConnectorApi.toBytecode())
                        continue
                    }
                    def inputStream = jarFile.getInputStream(entry)
                    try {
                        tmpJarOutputStream.putNextEntry(new ZipEntry(entry.name))
                        byte[] bytes = new byte[8192]
                        int bytesRead
                        while ((bytesRead = inputStream.read(bytes, 0, bytes.length)) != -1) {
                            tmpJarOutputStream.write(bytes, 0, bytesRead)
                        }
                    }
                    finally {
                        project.logger.info(name + " close inputStream for entry: ${entry.name}")
                        inputStream.close()
                    }
                }
            }
            finally {
                project.logger.info(name + " close tmpJarOutputStream")
                tmpJarOutputStream.close()
            }
            def jarName = tmpJar.name
            def md5Name = DigestUtils.md5Hex(tmpJar.getAbsolutePath())
            if (jarName.endsWith(".jar")) {
                jarName = jarName.substring(0, jarName.length() - 4)
            }
            def dest = transformInvocation.outputProvider.getContentLocation(jarName + md5Name, apiJar.contentTypes, apiJar.scopes, Format.JAR)
            FileUtils.copyFile(tmpJar, dest)
        }
        catch (Throwable throwable) {
            throwable.printStackTrace()
            throw new GradleException("exception happened while inject ConnectApi", throwable)
        }
        finally {
            project.logger.info(name + " 2 close jarFile: ${jarFile.name}")
            jarFile.close()
        }

        project.logger.info(name + " transform end")
    }

    private CtClass getCtClassFromFile(File file) {
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

    private void parseClass(CtClass ctClass, List<ConnectorBean> beans) {
        if (ctClass.isFrozen()) {
            ctClass.defrost()
        }
        if (ctClass.hasAnnotation(Connector.class)) {
            project.logger.info(name + " find class: ${ctClass.name}")
            ConnectorBean bean = new ConnectorBean()
            bean.connector = ctClass.getAnnotation(Connector.class)
            bean.ctClass = ctClass
            beans.add(bean)
        }
        else {
            ctClass.detach()
        }
    }

    private boolean parseJar(JarInput jarInput, List<ConnectorBean> beans) {
        JarFile jarFile = new JarFile(jarInput.file)
        try {
            Enumeration<JarEntry> entries = jarFile.entries()
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement()
                if (!entry.name.endsWith(".class")) {
                    continue
                }
                if (entry.name == "com/github/seelikes/android/plugin/connector/api/ConnectorApi.class") {
                    return true
                }
                CtClass ctClass = ClassPool.default.get(entry.name.replaceAll("/", ".").substring(0, entry.name.length() - 6))
                parseClass(ctClass, beans)
            }
        }
        catch (Throwable throwable) {
            throwable.printStackTrace()
            throw new GradleException("exception happened while parsing ${jarInput.file.absolutePath}", throwable)
        }
        finally {
            project.logger.info(name + " 1 close jarFile ${jarFile.name}")
            jarFile.close()
        }
        return false
    }
}
