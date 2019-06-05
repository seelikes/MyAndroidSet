<?xml version="1.0"?>
<#import "root://activities/common/kotlin_macros.ftl" as kt>
<recipe>
    <@kt.addAllKotlinDependencies />

    <#if fragmentLayoutRootComponent == "1" || fragmentLayoutRootComponent == "2">
        <merge from="root/res/values/strings.xml.ftl" to="${escapeXmlAttribute(resOut)}/values/strings.xml" />
        <open file="${escapeXmlAttribute(resOut)}/values/strings.xml" />
    </#if>

    <instantiate from="root/res/layout/fragment.xml.ftl"
                   to="${escapeXmlAttribute(resOut)}/layout/${escapeXmlAttribute(fragmentLayout)}.xml" />
    <open file="${escapeXmlAttribute(resOut)}/layout/${escapeXmlAttribute(fragmentLayout)}.xml" />

    <instantiate from="root/src/app_package/Fragment.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${fragmentClass}.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${fragmentClass}.${ktOrJavaExt}" />

    <instantiate from="root/src/app_package/ViewModel.${ktOrJavaExt}.ftl"
                 to="${escapeXmlAttribute(srcOut)}/${viewModelClass}.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${viewModelClass}.${ktOrJavaExt}" />

    <#if isCreateDataModelClass>
        <instantiate from="root/src/app_package/DataModel.${ktOrJavaExt}.ftl"
                     to="${escapeXmlAttribute(srcOut)}/${dataModelClass}.${ktOrJavaExt}" />
        <open file="${escapeXmlAttribute(srcOut)}/${dataModelClass}.${ktOrJavaExt}" />
    </#if>

</recipe>
