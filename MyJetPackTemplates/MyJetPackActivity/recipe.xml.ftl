<?xml version="1.0"?>
<#import "../common/kotlin_macros.ftl" as kt>
<recipe>
    <#--<#include "root://activities/common/recipe_manifest.xml.ftl" />-->
    <@kt.addAllKotlinDependencies />

    <merge from="root/AndroidManifest.xml.ftl" to="${escapeXmlAttribute(manifestOut)}/AndroidManifest.xml" />

    <merge from="root/res/values/strings.xml.ftl" to="${escapeXmlAttribute(resOut)}/values/strings.xml" />

    <instantiate from="root/res/layout/activity.xml.ftl"
                   to="${escapeXmlAttribute(resOut)}/layout/${escapeXmlAttribute(activityLayout)}.xml" />

    <instantiate from="root/src/app_package/Activity.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${activityClass}.${ktOrJavaExt}" />

    <instantiate from="root/src/app_package/ViewModel.${ktOrJavaExt}.ftl"
                 to="${escapeXmlAttribute(srcOut)}/${viewModelClass}.${ktOrJavaExt}" />

    <instantiate from="root/src/app_package/DataModel.${ktOrJavaExt}.ftl"
                 to="${escapeXmlAttribute(srcOut)}/${dataModelClass}.${ktOrJavaExt}" />

</recipe>
