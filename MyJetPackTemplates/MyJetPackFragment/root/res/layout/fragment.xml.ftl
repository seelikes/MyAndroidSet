<?xml version="1.0" encoding="utf-8"?>
<#if fragmentLayoutRootComponent == "0">
<layout xmlns:android="http://schemas.android.com/apk/res/android">
</#if>
<#if fragmentLayoutRootComponent == "1" || fragmentLayoutRootComponent == "2">
    <layout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto">
</#if>
    <data>
        <variable name="view" type="${escapeKotlinIdentifiers(packageName)}.${fragmentClass}" />
        <variable name="model" type="${escapeKotlinIdentifiers(packageName)}.${viewModelClass}" />
    </data>

    <#if fragmentLayoutRootComponent == "0">
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical">



        </LinearLayout>
    </#if>

    <#if fragmentLayoutRootComponent == "1" || fragmentLayoutRootComponent == "2">
        <${getMaterialComponentName('android.support.design.widget.CoordinatorLayout', useAndroidX)}
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <${getMaterialComponentName('android.support.design.widget.AppBarLayout', useAndroidX)}
                android:layout_width="match_parent"
                android:layout_height="wrap_content">

                <${getMaterialComponentName('android.support.design.widget.CollapsingToolbarLayout', useAndroidX)}
                    android:id="@+id/toolbar_layout"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    app:layout_scrollFlags="scroll|exitUntilCollapsed"
                    app:toolbarId="@+id/toolbar">

                    <${getMaterialComponentName('androidx.appcompat.widget.Toolbar', useAndroidX)}
                        android:id="@+id/toolbar"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        app:contentInsetStart="0px"
                        app:contentInsetEnd="0px">

                        <LinearLayout
                            android:layout_width="match_parent"
                            android:layout_height="216px"
                            android:background="@android:color/white"
                            android:gravity="bottom"
                            android:orientation="horizontal">

                            <RelativeLayout
                                android:layout_width="192px"
                                android:layout_height="152px"
                                android:onClick='@{v -> model.host.finish()}'>

                                <ImageView
                                    android:layout_width="64px"
                                    android:layout_height="64px"
                                    android:layout_centerInParent="true"
                                    android:contentDescription="@null"
                                    android:scaleType="fitXY"
                                    android:src="@mipmap/ic_left_arrow" />

                            </RelativeLayout>

                            <View
                                android:layout_width="0px"
                                android:layout_height="1px"
                                android:layout_weight="1" />

                            <TextView
                                android:layout_width="wrap_content"
                                android:layout_height="152px"
                                android:gravity="center_vertical"
                                android:textSize="48px"
                                android:textColor="@color/room_title"
                                android:text='@{model.title}' />

                            <View
                                android:layout_width="0px"
                                android:layout_height="1px"
                                android:layout_weight="1" />

                            <RelativeLayout
                                android:layout_width="192px"
                                android:layout_height="152px">



                            </RelativeLayout>

                        </LinearLayout>

                    </${getMaterialComponentName('androidx.appcompat.widget.Toolbar', useAndroidX)}>

                </${getMaterialComponentName('android.support.design.widget.CollapsingToolbarLayout', useAndroidX)}>

            </${getMaterialComponentName('android.support.design.widget.AppBarLayout', useAndroidX)}>

        </${getMaterialComponentName('android.support.design.widget.CoordinatorLayout', useAndroidX)}>

        <#if fragmentLayoutRootComponent == "1">
            <${getMaterialComponentName('android.support.v4.widget.NestedScrollView', useAndroidX)}
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                app:layout_behavior="@string/appbar_scrolling_view_behavior">



            </${getMaterialComponentName('android.support.v4.widget.NestedScrollView', useAndroidX)}>
        </#if>

        <#if fragmentLayoutRootComponent == "2">
            <${getMaterialComponentName('android.support.v7.widget.RecyclerView', useAndroidX)}
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                app:layout_behavior="@string/appbar_scrolling_view_behavior">



            </${getMaterialComponentName('android.support.v7.widget.RecyclerView', useAndroidX)}>
        </#if>
    </#if>
</layout>