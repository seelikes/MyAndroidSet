package com.github.seelikes.android.plugin.business.threads

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.TransformInvocation
import com.github.seelikes.android.plugin.business.BusinessParserTask
import com.github.seelikes.android.plugin.business.utils.DirectoryCtClassForger
import javassist.ClassPool
import javassist.CtClass
import javassist.NotFoundException
import org.gradle.api.Project
/**
 * Created by liutiantian on 2020-11-16 15:30 星期一
 */
class DirectoryInputParser extends BusinessParserTask<DirectoryParser> implements DirectoryCtClassForger {
    interface DirectoryParser {
        void parse(Project project, TransformInvocation transformInvocation, DirectoryInput directoryInput, CtClass ctClass, File file)
    }

    private DirectoryInput input

    DirectoryInputParser(Project project, TransformInvocation transformInvocation, DirectoryInput input) {
        super(project, transformInvocation)
        this.input = input
    }

    @Override
    void run() {
        project.logger.info("dir.2216 ${input.name} start")
        input.each { DirectoryInput directoryInput ->
            directoryInput.file.eachFileRecurse { File file ->
                CtClass ctClass = getCtClassFromFile(file)
                if (ctClass != null) {
                    loopParser { DirectoryParser parser ->
                        parser.parse(project, transformInvocation, directoryInput, ctClass, file)
                    }
                }
            }
            copyDirectory(project, transformInvocation, directoryInput, directoryInput.file)
        }
        project.logger.info("dir.2216 ${input.name} end")
    }

    protected static CtClass getCtClassFromFile(File file, String[] packageNames) {
        if (file == null) {
            return null
        }
        if (!file.name.endsWith(".class")) {
            return null
        }
        if (packageNames != null && packageNames.length > 0) {
            def packagePath = file.absolutePath.replaceAll("\\\\", ".")
            for (String packageName : packageNames) {
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
}
