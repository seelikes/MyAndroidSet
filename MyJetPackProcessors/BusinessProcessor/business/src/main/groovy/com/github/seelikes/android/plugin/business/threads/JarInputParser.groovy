package com.github.seelikes.android.plugin.business.threads


import com.android.build.api.transform.JarInput
import com.android.build.api.transform.TransformInvocation
import com.github.seelikes.android.plugin.business.BusinessParserTask
import com.github.seelikes.android.plugin.business.utils.JarMethods
import javassist.ClassPool
import javassist.CtClass
import org.gradle.api.GradleException
import org.gradle.api.Project

import java.util.jar.JarEntry
import java.util.jar.JarFile

/**
 * Created by liutiantian on 2020-11-16 15:12 星期一
 */
class JarInputParser extends BusinessParserTask<JarParser> implements JarMethods {
    interface JarParser {
        void parse(Project project, TransformInvocation transformInvocation, JarInput jarInput, CtClass ctClass)
    }

    private JarInput input

    JarInputParser(Project project, TransformInvocation transformInvocation, JarInput input) {
        super(project, transformInvocation)
        this.input = input
    }

    @Override
    void run() {
        project.logger.info("jar.2216 ${System.currentTimeMillis()} ${input.name} start")
        JarFile jarFile = new JarFile(input.file)
        try {
            Enumeration<JarEntry> entries = jarFile.entries()
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement()
                if (!entry.name.endsWith(".class")) {
                    continue
                }
                CtClass ctClass = ClassPool.default.get(entry.name.replaceAll("/", ".").substring(0, entry.name.length() - 6))
                loopParser { JarParser parser ->
                    parser.parse(project, transformInvocation, input, ctClass)
                }
            }
            copyJar(project, transformInvocation, input, input.file)
        }
        catch (Throwable throwable) {
            throwable.printStackTrace()
            throw new GradleException("exception happened while parsing ${input.file.absolutePath}", throwable)
        }
        finally {
            jarFile.close()
        }
        project.logger.info("jar.2216 ${System.currentTimeMillis()} ${input.name} end")
    }
}
