package com.github.seelikes.android.plugin.business

import com.android.build.api.transform.TransformInvocation
import com.github.seelikes.android.plugin.business.threads.DirectoryInputParser
import com.github.seelikes.android.plugin.business.threads.JarInputParser
import org.gradle.api.Project

/**
 * Created by liutiantian on 2020-11-16 19:46 星期一
 */
interface BusinessParser extends JarInputParser.JarParser, DirectoryInputParser.DirectoryParser {
    boolean isIncremental()

    void collect(Project project, TransformInvocation transformInvocation)
}
