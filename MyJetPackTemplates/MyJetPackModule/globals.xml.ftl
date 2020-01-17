<?xml version="1.0"?>
<globals>
    <#include "root://gradle-projects/common/globals.xml.ftl" />
    <#include "root://gradle-projects/common/globals_android_module.xml.ftl" />
    <#assign useAndroidX=isAndroidxEnabled()>

    <global id="useAndroidX" type="boolean" value="${useAndroidX?string}" />
    <#include "root://activities/common/kotlin_globals.xml.ftl" />
    <global id="moduleNameShiftPrefix" type="string" value="${appTitle?substring(appTitle?index_of('-') + 1)}" />
    <global id="moduleNameUnderscore" type="string" value="${appTitle?replace('-', '_')}" />
    <global id="aloneDir" type="string" value="${escapeXmlAttribute(projectOut)}/src/alone" />
    <global id="aloneOut" type="string" value="${escapeXmlAttribute(projectOut)}/src/alone/java/${slashedPackageName(packageName)}" />
    <global id="aloneResOut" type="string" value="${escapeXmlAttribute(projectOut)}/src/alone/res" />
</globals>
