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

-keep class com.example.myjetpackapplication.sophix.SophixStubApplication.RealApplicationStub

#androidx
-keep androidx.** { *; }
-dontwarn androidx.**

#arouter
-keep com.alibaba.android.arouter.** { *; }

#hotfix
-keep class com.taobao.sophix.** { *; }
-keep class com.ta.utdid2.device.** { *; }

#aliyun
-keep com.alibaba.mtl.** { *; }
-keep com.taobao.statistic.** { *; }
-keep com.ut.mini.** { *; }
-keep com.ta.utdid2.** { *; }
-keep com.ut.device.** { *; }

#floatingactionbutton
-keep com.getbase.floatingactionbutton.** { *; }

#SwipeDelMenuLayout
-keep com.mcxtzhang.swipemenulib.** { *; }

#findbugs
-keep javax.annotation.** { *; }

#gson
-keep com.google.gson.** { *; }

#dagger
-keep dagger.** { *; }
-dontwarn dagger.**

#google
-keep com.google.** { *; }
-dontwarn com.google.**

#immersionbar
-keep com.gyf.immersionbar.** { *; }

#logger
-keep com.orhanobut.logger.** { *; }

#squareup
-keep com.squareup.** { *; }
-dontwarn com.squareup.**

#AndPermission
-keep com.yanzhenjie.permission.** { *; }

#rxjava
-keep io.reactivex.** { *; }

#javax
-keep javax.** { *; }
-dontwarn javax.**

#junit
-keep junit.** { *; }
-dontwarn junit.**
-keep org.junit.** { *; }
-dontwarn org.junit.**

#autosize
-keep  me.jessyan.autosize.** { *; }

#org
-keep org.** { *; }

#EventBus
-keep org.greenrobot.eventbus.** { *; }

#kotlin
-keep kotlinx.android.** { *; }
-dontwarn kotlinx.android.**
-keep kotlin.** { *; }
-dontwarn kotlin.**
-kepp kotlin.coroutines.android.** { *; }
-dontwarn kotlin.coroutines.android.**
-keep kotlin.coroutines.** { *; }
-dontwarn kotlin.coroutines.**