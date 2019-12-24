<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <data>
        <variable
            name="model"
            type="${packageName}.${underscoreToCamelCase(activitySingleLayout)}ViewModel" />
    </data>

    <androidx.coordinatorlayout.widget.CoordinatorLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <com.google.android.material.appbar.AppBarLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">

            <com.google.android.material.appbar.CollapsingToolbarLayout
                android:id="@+id/toolbar_layout"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                app:layout_scrollFlags="scroll|exitUntilCollapsed"
                app:toolbarId="@+id/toolbar">

                <androidx.appcompat.widget.Toolbar
                    android:id="@+id/toolbar"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    app:contentInsetEnd="0px"
                    app:contentInsetStart="0px">

                    <LinearLayout
                        android:layout_width="match_parent"
                        android:layout_height="216px"
                        android:background="@android:color/white"
                        android:gravity="bottom"
                        android:orientation="horizontal">

                        <RelativeLayout
                            android:layout_width="192px"
                            android:layout_height="152px"
                            android:onClick='@{view -> model.host.finish()}'>

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
                            android:text='@{model.title}'
                            android:textColor="@color/title_text_color"
                            android:textSize="48px" />

                        <View
                            android:layout_width="0px"
                            android:layout_height="1px"
                            android:layout_weight="1" />

                        <RelativeLayout
                            android:layout_width="192px"
                            android:layout_height="152px">


                        </RelativeLayout>

                    </LinearLayout>

                </androidx.appcompat.widget.Toolbar>

            </com.google.android.material.appbar.CollapsingToolbarLayout>

        </com.google.android.material.appbar.AppBarLayout>

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/rv_list"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:layout_behavior="@string/appbar_scrolling_view_behavior"
            android:background="@android:color/white" />

    </androidx.coordinatorlayout.widget.CoordinatorLayout>
</layout>