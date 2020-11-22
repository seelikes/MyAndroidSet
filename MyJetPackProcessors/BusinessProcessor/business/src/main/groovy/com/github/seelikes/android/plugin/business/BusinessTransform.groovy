package com.github.seelikes.android.plugin.business

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.github.seelikes.android.plugin.business.plugins.router.RouterExtension
import com.github.seelikes.android.plugin.business.plugins.router.RouterParser
import com.github.seelikes.android.plugin.business.threads.DirectoryInputParser
import com.github.seelikes.android.plugin.business.threads.JarInputParser
import javassist.ClassPool
import org.gradle.api.Project

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by liutiantian on 2020-11-16 14:15 星期一
 */
class BusinessTransform extends Transform {
    private Project project

    BusinessTransform(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return "Business"
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
        project.logger.info("${name}.2216 transform begin")

        for (def classPath in project.android.bootClasspath) {
            ClassPool.default.appendClassPath classPath.toString()
        }

        // 此处无视增量
        transformInvocation.inputs.each { input ->
            input.jarInputs.each { jarInput ->
                ClassPool.default.appendClassPath(jarInput.file.absolutePath)
            }

            input.directoryInputs.each { DirectoryInput directoryInput ->
                ClassPool.default.appendClassPath(directoryInput.file.absolutePath)
            }
        }

        transformInvocation.referencedInputs.each { input ->
            input.jarInputs.each { jarInput ->
                ClassPool.default.appendClassPath(jarInput.file.absolutePath)
            }

            input.directoryInputs.each { DirectoryInput directoryInput ->
                ClassPool.default.appendClassPath(directoryInput.file.absolutePath)
            }
        }

        List<BusinessParser> parsers = new ArrayList<>()
        parsers.add(new RouterParser(project.extensions.getByType(RouterExtension)))

        ExecutorService threadPool = Executors.newFixedThreadPool(7)
        List<Runnable> tasks = new ArrayList<>()

        if (transformInvocation.isIncremental()) {
            // 增量处理区域
            transformInvocation.secondaryInputs
        } else {
            // 全量处理区域
            transformInvocation.inputs.each { TransformInput input ->
                input.jarInputs.each { JarInput jarInput ->
                    JarInputParser parser = new JarInputParser(project, transformInvocation, jarInput)
                    parser.addParser(parsers)
                    tasks.add(parser)
                }

                input.directoryInputs.each { DirectoryInput directoryInput ->
                    DirectoryInputParser parser = new DirectoryInputParser(project, transformInvocation, directoryInput)
                    parser.addParser(parsers)
                    tasks.add(parser)
                }
            }
        }

        CompletableFuture<?>[] futures = tasks.stream()
            .map({ task -> CompletableFuture.runAsync(task, threadPool) })
            .toArray {
                return new CompletableFuture<?>[it]
            }
        CompletableFuture.allOf(futures).join()
        threadPool.shutdown()

        parsers.each {
            it.collect(project, transformInvocation)
        }

        project.logger.info("${name}.2216 transform finish")
    }
}
