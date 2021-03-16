package com.github.seelikes.android.plugin.business.utils

import com.android.build.api.transform.JarInput
import com.android.build.api.transform.TransformInvocation
import javassist.ClassPool
import javassist.CtClass
import org.gradle.api.GradleException
import org.gradle.api.Project

import java.util.function.Function
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream

/**
 * Created by liutiantian on 2020-11-20 08:14 星期五
 */
trait JarCtClassForger implements JarMethods {
    void changeCtClassInJar(Project project, TransformInvocation transformInvocation, JarInput jarInput, Function<CtClass, Boolean> function) {
        JarFile jarFile = new JarFile(jarInput.file)
        try {
            File tmpJarFile = new File(transformInvocation.context.temporaryDir, jarInput.file.name)
            JarOutputStream tmpJarOutputStream = new JarOutputStream(new FileOutputStream(tmpJarFile))
            try {
                Enumeration<JarEntry> entries = jarFile.entries()
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement()
                    if (!entry.name.endsWith(".class")) {
                        copyEntry(jarFile, entry, tmpJarOutputStream, new JarEntry(entry.name))
                        continue
                    }
                    CtClass ctClass = ClassPool.default.get(entry.name.replaceAll("/", ".").substring(0, entry.name.length() - 6))
                    if (ctClass == null) {
                        continue
                    }
                    if (ctClass.isFrozen()) {
                        ctClass.defrost()
                    }
                    try {
                        Boolean changed = function.apply(ctClass)
                        if (changed) {
                            ctClass.detach()
                            writeEntry(ctClass.toBytecode(), tmpJarOutputStream, new JarEntry(entry.name))
                        } else {
                            copyEntry(jarFile, entry, tmpJarOutputStream, new JarEntry(entry.name))
                        }
                    }
                    catch (Throwable throwable) {
                        throwable.printStackTrace()
                        throw new GradleException("error in forge Class: ${ctClass.name} in Jar", throwable)
                    }
                }
            }
            finally {
                tmpJarOutputStream.flush()
                tmpJarOutputStream.close()
            }
            copyJar(project, transformInvocation, jarInput, tmpJarFile)
            deleteJar(project, transformInvocation, jarInput)
        }
        catch (Throwable throwable) {
            throwable.printStackTrace()
            project.logger.info("ha ha ha")
            throw new GradleException("exception hanppened while change Jar: ${jarInput.name}")
        }
        finally {
            jarFile.close()
        }
    }

    private void copyEntry(JarFile fromFile, JarEntry fromEntry, JarOutputStream toOutputStream, JarEntry toEntry) {
        InputStream inputStream = fromFile.getInputStream(fromEntry)
        try {
            toOutputStream.putNextEntry(toEntry)
            byte[] bytes = new byte[8192]
            int bytesRead
            while ((bytesRead = inputStream.read(bytes, 0, bytes.length)) != -1) {
                toOutputStream.write(bytes, 0, bytesRead)
            }
            toOutputStream.flush()
        }
        finally {
            inputStream.close()
        }
    }

    private void writeEntry(byte[] bytes, JarOutputStream toOutputStream, JarEntry toEntry) {
        toOutputStream.putNextEntry(toEntry)
        toOutputStream.write(bytes, 0, bytes.length)
        toOutputStream.flush()
    }
}