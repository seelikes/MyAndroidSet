package com.github.seelikes.android.plugin.business.utils

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.TransformInvocation
import javassist.CtClass
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

/**
 * Created by liutiantian on 2020-11-20 23:41 星期五
 */
trait DirectoryCtClassForger {
    /**
     * 这里只是处理善后
     * 修改后的文件转移操作
     * @param project 插件处理的项目
     * @param transformInvocation 转换上下文
     * @param directoryInput 修改的类所在的目录
     * @param ctClass 修改后的类对象
     */
    void changeCtClassInDirectory(Project project, TransformInvocation transformInvocation, DirectoryInput directoryInput, CtClass ctClass) {
        ctClass.writeFile(new File(transformInvocation.context.temporaryDir, "classes").absolutePath)
        ctClass.detach()

        FileUtils.copyDirectory(new File(transformInvocation.context.temporaryDir, "classes"), transformInvocation.outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY))
    }
}