<?xml version="1.0"?>
<recipe>
    <merge from="root/settings.gradle.ftl"
        to="${escapeXmlAttribute(topOut)}/settings.gradle" />

    <instantiate from="root/.gitignore.ftl"
        to="${escapeXmlAttribute(projectOut)}/.gitignore" />

    <instantiate from="root/build.gradle.ftl"
        to="${escapeXmlAttribute(projectOut)}/build.gradle" />

    <instantiate from="root/proguard-rules.pro.ftl"
        to="${escapeXmlAttribute(projectOut)}/proguard-rules.pro" />

    <instantiate from="root/src/androidTest/java/app_package/ExampleInstrumentedTest.kt.ftl"
        to="${escapeXmlAttribute(testOut)}/ExampleInstrumentedTest.kt" />

<#if !createActivity>
    <mkdir at="${escapeXmlAttribute(srcOut)}" />
</#if>

    <instantiate from="root/src/main/res/values/strings.xml.ftl"
        to="${escapeXmlAttribute(resOut)}/values/strings.xml" />

    <instantiate from="root/src/test/java/app_package/ExampleUnitTest.kt.ftl"
        to="${escapeXmlAttribute(unitTestOut)}/ExampleUnitTest.kt" />

    <instantiate from="root/src/main/AndroidManifest.xml.ftl"
        to="${escapeXmlAttribute(manifestOut)}/AndroidManifest.xml" />
</recipe>
