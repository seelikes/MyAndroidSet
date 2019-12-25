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

    <instantiate from="root/consumer-rules.pro.ftl"
        to="${escapeXmlAttribute(projectOut)}/consumer-rules.pro" />

    <instantiate from="root/src/androidTest/java/app_package/ExampleInstrumentedTest.kt.ftl"
        to="${escapeXmlAttribute(testOut)}/ExampleInstrumentedTest.kt" />

<#if !createActivity>
    <mkdir at="${escapeXmlAttribute(srcOut)}" />
</#if>

    <instantiate from="root/src/alone/app_package/SingleRunApplication.kt.ftl"
        to="${escapeXmlAttribute(aloneOut)}/${underscoreToCamelCase(activitySingleLayout)}Application.kt" />

    <instantiate from="root/src/alone/app_package/SingleRunActivity.kt.ftl"
        to="${escapeXmlAttribute(aloneOut)}/${underscoreToCamelCase(activitySingleLayout)}Activity.kt" />

    <instantiate from="root/src/alone/app_package/SingleRunDataModel.kt.ftl"
        to="${escapeXmlAttribute(aloneOut)}/${underscoreToCamelCase(activitySingleLayout)}DataModel.kt" />

    <instantiate from="root/src/alone/app_package/SingleRunItemAdapter.kt.ftl"
        to="${escapeXmlAttribute(aloneOut)}/${underscoreToCamelCase(activitySingleLayout)}ItemAdapter.kt" />

    <instantiate from="root/src/alone/app_package/SingleRunItemHolder.kt.ftl"
        to="${escapeXmlAttribute(aloneOut)}/${underscoreToCamelCase(activitySingleLayout)}ItemHolder.kt" />

    <instantiate from="root/src/alone/app_package/SingleRunViewModel.kt.ftl"
        to="${escapeXmlAttribute(aloneOut)}/${underscoreToCamelCase(activitySingleLayout)}ViewModel.kt" />

    <instantiate from="root/src/alone/res/layout/activity_single_run.xml.ftl"
        to="${escapeXmlAttribute(aloneResOut)}/layout/activity_${activitySingleLayout}.xml" />

    <instantiate from="root/src/alone/res/layout/item_single_run.xml.ftl"
        to="${escapeXmlAttribute(aloneResOut)}/layout/item_${activitySingleLayout}.xml" />

    <instantiate from="root/src/alone/res/values/strings.xml.ftl"
        to="${escapeXmlAttribute(aloneResOut)}/values/strings.xml" />

    <instantiate from="root/src/alone/AndroidManifest.xml.ftl"
        to="${escapeXmlAttribute(aloneDir)}/AndroidManifest.xml" />

    <instantiate from="root/src/main/res/values/strings.xml.ftl"
        to="${escapeXmlAttribute(resOut)}/values/strings.xml" />

    <instantiate from="root/src/test/java/app_package/ExampleUnitTest.kt.ftl"
        to="${escapeXmlAttribute(unitTestOut)}/ExampleUnitTest.kt" />

    <instantiate from="root/src/main/AndroidManifest.xml.ftl"
        to="${escapeXmlAttribute(manifestOut)}/AndroidManifest.xml" />
</recipe>
