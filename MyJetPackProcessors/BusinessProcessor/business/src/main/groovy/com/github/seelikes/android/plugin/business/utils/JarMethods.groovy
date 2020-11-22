package com.github.seelikes.android.plugin.business.utils

import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.TransformInvocation
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

/**
 * Created by liutiantian on 2020-11-21 12:37 星期六
 */
trait JarMethods {
    void copyJar(Project project, TransformInvocation transformInvocation, JarInput jarInput, File jarFile) {
        def jarName = jarFile.name
        def dest = transformInvocation.outputProvider.getContentLocation(jarName, jarInput.contentTypes, jarInput.scopes, Format.JAR)
        FileUtils.copyFile(jarFile, dest)
    }
}