package com.example.myjetpackapplication.plugin.lifecycle

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by liutiantian on 2020-01-02 11:29 星期四
 */
class LifecyclePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        def base = project.extensions.getByType(BaseExtension)
        if (base) {
            base.registerTransform(new LifecycleTransform(project, project.extensions.create("lifecycle", LifecycleExtension)))
        }
    }
}
