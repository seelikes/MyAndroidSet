package com.example.myjetpackapplication.plugin.lifecycle

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import javassist.ClassPool
import javassist.CtClass
import org.apache.commons.io.FileUtils

/**
 * Created by liutiantian on 2020-01-02 12:08 星期四
 */
class LifecycleTransform extends Transform {
    private LifecycleExtension extension

    LifecycleTransform(LifecycleExtension extension) {
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
        transformInvocation.inputs.parallelStream().each { input ->
            input.directoryInputs.parallelStream().each { directoryInput ->
                ClassPool.default.appendClassPath(directoryInput.file.absolutePath)
                directoryInput.file.eachFileRecurse { file ->
                    if (!file.name.endsWith(".class") || (file.name.matches(/^R\$[a-zA-Z]+?\.class$/) || file.name == "R.class")) {
                        return
                    }
                    def canonicalName = file.name.substring(0, file.name.length() - 6)
                    def ctClass = ClassPool.default.getCtClass(canonicalName)
                    if (ctClass.isFrozen()) {
                        ctClass.defrost()
                    }
                    ctClass.declaredMethods.each { declaredMethod ->
                        if (declaredMethod.name.startsWith("on") && declaredMethod.declaringClass != ctClass) {
                            declaredMethod.insertBefore("Log.i();")
                            declaredMethod.insertAfter("Log.i();")
                        }
                    }
                    def dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                    FileUtils.copyDirectory(directoryInput.file, dest)
                }
            }
        }
    }
}
