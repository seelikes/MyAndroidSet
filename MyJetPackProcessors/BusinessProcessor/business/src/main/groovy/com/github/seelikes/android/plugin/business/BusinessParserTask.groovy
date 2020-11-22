package com.github.seelikes.android.plugin.business

import com.android.build.api.transform.JarInput
import com.android.build.api.transform.TransformInvocation
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import javassist.CtClass
import org.gradle.api.Project

/**
 * Created by liutiantian on 2020-11-16 15:21 星期一
 */
abstract class BusinessParserTask<Parser> implements Runnable {
    protected final List<Parser> parsers = new ArrayList<>()
    protected final Project project
    protected final TransformInvocation transformInvocation

    BusinessParserTask(Project project, TransformInvocation transformInvocation) {
        this.project = project
        this.transformInvocation = transformInvocation
    }

    void addParser(Parser parser) {
        synchronized (parsers) {
            if (parsers.contains(parser)) {
                return
            }
            parsers.add(parser)
        }
    }

    void addParser(List<Parser> parsers) {
        synchronized (parsers) {
            if (parsers == null || parsers.isEmpty()) {
                return
            }
            parsers.each {
                addParser(it)
            }
        }
    }

    void removeParser(Parser parser) {
        synchronized (parsers) {
            parsers.remove(parser)
        }
    }

    void loopParser(Closure<Parser> closure) {
        synchronized (parsers) {
            parsers.each {
                closure(it)
            }
        }
    }

    protected Map<Business, CtClass> parseClass(CtClass ctClass) {

    }

    protected Map<Business, CtClass> parseJar(JarInput jarInput) {

    }
}
