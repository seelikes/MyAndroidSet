<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">
    <data>
        <import type="android.view.View" />
        <variable name="model" type="${packageName}.Single${moduleName?cap_first}ItemHolder" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center_vertical"
        android:onClick='@{view -> model.onUiClick()}'>

        <androidx.appcompat.widget.AppCompatTextView
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:layout_marginStart="64px"
            android:layout_marginLeft="64px"
            android:gravity="center_vertical"
            android:textColor="@color/title_text_color"
            android:textSize="48px"
            android:text='@{model.title}' />

        <View
            android:layout_width="0px"
            android:layout_height="200px"
            android:layout_weight="1" />

        <ImageView
            android:layout_width="64px"
            android:layout_height="64px"
            android:layout_marginEnd="64px"
            android:layout_marginRight="64px"
            android:scaleType="fitXY"
            android:contentDescription="@null"
            android:src="@mipmap/ic_right_arrow"
            android:visibility='@{model.hasChildren ? View.VISIBLE : View.GONE}' />
    </LinearLayout>
</layout>