package com.github.seelikes.android.plugin.business

import com.android.build.gradle.BaseExtension
import com.github.seelikes.android.plugin.business.plugins.router.RouterExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by liutiantian on 2020-11-16 14:13 星期一
 */
class BusinessPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.logger.info("here go go")
        project.dependencies.add("implementation", "com.example.myjetpackapplication.annotationprocessor:business-annotation:1.0.14")
        project.dependencies.add("implementation", "com.example.myjetpackapplication.annotationprocessor:business-api:1.0.12")
        def base = project.extensions.getByType(BaseExtension)
        if (base) {
            project.extensions.create("router", RouterExtension)
            base.registerTransform(new BusinessTransform(project))
        }
    }
}
