# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-dontoptimize
#-applymapping E:\Docs\mapping-1.2.txt
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-dontshrink
-verbose
-allowaccessmodification
-keepattributes InnerClasses,Signature,SourceFile,LineNumberTable,*Annotation*

-keepclassmembers class * implements java.io.Serializable {
    !static !transient <fields>;
    *** get*();
    *** set*(***);
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers class * {
    *** serialVersionUID;
}

-keepclassmembers class com.example.myjetpackapplication.MyJetPackApplication {
    public <init>();
}

-keep class com.example.myjetpackapplication.sophix.** { *; }

#androidx
-keep class androidx.** { *; }
-dontwarn androidx.**

#arouter
-keep class com.alibaba.android.arouter.** { *; }

#hotfix
-keep class com.taobao.sophix.** { *; }
-keep class com.ta.utdid2.device.** { *; }

#aliyun
-keep class com.alibaba.mtl.** { *; }
-keep class com.taobao.statistic.** { *; }
-keep class com.ut.mini.** { *; }
-keep class com.ta.utdid2.** { *; }
-keep class com.ut.device.** { *; }

#floatingactionbutton
-keep class com.getbase.floatingactionbutton.** { *; }

#SwipeDelMenuLayout
-keep class com.mcxtzhang.swipemenulib.** { *; }

#findbugs
-keep class javax.annotation.** { *; }

#gson
-keep class com.google.gson.** { *; }

#dagger
-keep class dagger.** { *; }
-dontwarn dagger.**

#google
-keep class com.google.** { *; }
-dontwarn com.google.**

#immersionbar
-keep class com.gyf.immersionbar.** { *; }

#logger
-keep class com.orhanobut.logger.** { *; }

#squareup
-keep class com.squareup.** { *; }
-dontwarn com.squareup.**

#AndPermission
-keep class com.yanzhenjie.permission.** { *; }

#rxjava
-keep class io.reactivex.** { *; }

#javax
-keep class javax.** { *; }
-dontwarn javax.**

#junit
-keep class junit.** { *; }
-dontwarn junit.**
-keep class org.junit.** { *; }
-dontwarn org.junit.**

#autosize
-keep class  me.jessyan.autosize.** { *; }

#org
-keep class org.** { *; }

#EventBus
-keep class org.greenrobot.eventbus.** { *; }

#kotlin
-keep class kotlinx.android.** { *; }
-dontwarn kotlinx.android.**
-keep class kotlin.** { *; }
-dontwarn kotlin.**
-keep class kotlinx.coroutines.android.** { *; }
-dontwarn kotlinx.coroutines.android.**
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**