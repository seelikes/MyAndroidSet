package com.example.myjetpackapplication.plugin.realm

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by liutiantian on 2020-01-02 11:29 星期四
 */
class RealmPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.dependencies.add("implementation", "com.github.seelikes.android:realm-annotation:1.0.03")
        project.dependencies.add("implementation", "com.github.seelikes.android:realm-api:1.0.03")
        def base = project.extensions.getByType(BaseExtension)
        if (base) {
            base.registerTransform(new RealmTransform(project, project.extensions.create("realmHelper", RealmExtension)))
        }
    }
}
