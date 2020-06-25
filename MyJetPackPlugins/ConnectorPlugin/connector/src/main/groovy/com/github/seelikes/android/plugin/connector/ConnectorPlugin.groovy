package com.github.seelikes.android.plugin.connector

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by liutiantian on 2020-06-21 14:31 星期日
 */
class ConnectorPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.dependencies.add("implementation", "com.github.seelikes.android.plugin:connector-annotation:1.0.00")
        project.dependencies.add("implementation", "com.github.seelikes.android.plugin:connector-api:1.0.00")
        def base = project.extensions.getByType(BaseExtension)
        if (base) {
            base.registerTransform(new ConnectorTransform(project, project.extensions.create("connector", ConnectorExtension)))
        }
    }
}
