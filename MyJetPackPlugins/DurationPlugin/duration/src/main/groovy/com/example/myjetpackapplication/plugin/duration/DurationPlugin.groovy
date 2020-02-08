package com.example.myjetpackapplication.plugin.duration

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by liutiantian on 2020-01-29 04:35 星期三
 */
class DurationPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.dependencies.add("implementation", "com.github.seelikes.android:duration-api:1.0.00")
        def base = project.extensions.getByType(BaseExtension)
        if (base) {
            base.registerTransform(new DurationTransform(project, project.extensions.create("duration", DurationExtension)))
        }
    }
}
