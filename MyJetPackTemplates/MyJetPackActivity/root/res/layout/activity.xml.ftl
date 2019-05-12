<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">
    <data>
        <variable name="model" type="${escapeKotlinIdentifiers(packageName)}.${viewModelClass}" />
    </data>

    <${getMaterialComponentName('android.support.constraint.ConstraintLayout', useAndroidX)}
            android:layout_width="match_parent"
            android:layout_height="match_parent">



    </${getMaterialComponentName('android.support.constraint.ConstraintLayout', useAndroidX)}>
</layout>