<?xml version="1.0"?>
<#import "../common/kotlin_macros.ftl" as kt>
<recipe>
    <#--<#include "root://activities/common/recipe_manifest.xml.ftl" />-->
    <@kt.addAllKotlinDependencies />

<#if !(hasDependency('org.jetbrains.kotlin:kotlin-stdlib-jdk8'))>
    <dependency mavenUrl=org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version" />
</#if>

<#if !(hasDependency('com.github.seelikes.android:mvvm-basic'))>
    <dependency mavenUrl="com.github.seelikes.android:mvvm-basic:$mvvm_basic_version" />
</#if>

    <merge from="root/src/main/AndroidManifest.xml.ftl" to="${escapeXmlAttribute(manifestOut)}/AndroidManifest.xml" />
<#if canModuleSingleRun>
    <merge from="root/src/alone/AndroidManifest.xml.ftl" to="${escapeXmlAttribute(aloneDir)}/AndroidManifest.xml" />
</#if>

    <merge from="root/res/values/strings.xml.ftl" to="${escapeXmlAttribute(resOut)}/values/strings.xml" />

    <merge from="root/build.gradle.ftl" to="${escapeXmlAttribute(projectOut)}/build.gradle" />

    <instantiate from="root/res/layout/activity.xml.ftl"
        to="${escapeXmlAttribute(resOut)}/layout/${escapeXmlAttribute(activityLayout)}.xml" />

    <instantiate from="root/src/main/app_package/Activity.${ktOrJavaExt}.ftl"
        to="${escapeXmlAttribute(srcOut)}/${activityClass}.${ktOrJavaExt}" />

    <instantiate from="root/src/main/app_package/ViewModel.${ktOrJavaExt}.ftl"
        to="${escapeXmlAttribute(srcOut)}/${viewModelClass}.${ktOrJavaExt}" />

    <instantiate from="root/src/main/app_package/DataModel.${ktOrJavaExt}.ftl"
        to="${escapeXmlAttribute(srcOut)}/${dataModelClass}.${ktOrJavaExt}" />

</recipe>
