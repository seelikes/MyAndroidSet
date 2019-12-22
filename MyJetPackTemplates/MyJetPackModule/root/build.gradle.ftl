if (Run_Mode.contains('ALONE_${moduleName?upper_case}')) {
    apply plugin: 'com.android.application'
}
else {
    apply plugin: 'com.android.library'
}
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'
apply plugin: 'kotlin-kapt'

android {
    compileSdkVersion sdk_version_compile as int

    defaultConfig {
        minSdkVersion sdk_version_min as int
        targetSdkVersion sdk_version_target as int
        versionCode 1000
        versionName "1.0.00"
        if (Run_Mode.contains('ALONE_${moduleName?upper_case}')) {
            multiDexEnabled true
        }

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }

    if (Run_Mode.contains('ALONE_${moduleName?upper_case}')) {
        sourceSets {
            main {
                manifest.srcFile 'src/alone/AndroidManifest.xml'
                java {
                    srcDirs = ['src/main/java', 'src/alone/java']
                }
            }
        }
    }

    compileOptions {
        targetCompatibility JavaVersion.VERSION_1_8
        sourceCompatibility JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    kapt {
        arguments {
            arg("AROUTER_MODULE_NAME", project.name)
        }
    }

    dataBinding {
        enabled true
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"
    implementation "androidx.core:core-ktx:$core_ktx_version"
    implementation "androidx.appcompat:appcompat:$appcompat_version"
    implementation "androidx.constraintlayout:constraintlayout:$constraintlayout_version"
    implementation "com.google.android.material:material:$material_version"
    testImplementation "junit:junit:$junit_version"
    androidTestImplementation "androidx.test.ext:junit-ktx:$androidx_junit_ktx_version"
    androidTestImplementation "androidx.test:runner:$runner_version"
    androidTestImplementation "androidx.test.espresso:espresso-core:$espresso_core_version"
    implementation "com.example.myjetpackapplication:resources:$resources_version"
    implementation "com.example.myjetpackapplication.annotationprocessor:business-annotation:$business_annotation_version"
    kapt "com.example.myjetpackapplication.annotationprocessor:business-compiler:$business_compiler_version"
    api "com.github.seelikes.android:mvvm-basic:$mvvm_basic_version"
    api "com.java.lib:oil:$oil_version"
    api "com.orhanobut:logger:$logger_orhanobut_version"
    if (Run_Mode.contains('ALONE_${moduleName?upper_case}')) {
        implementation "com.example.myjetpackapplication:single:$single_version"
    }
}

if (!Run_Mode.contains('ALONE_${moduleName?upper_case}')) {
    /**
    * 发布必须配置此项
    * 如果发布至maven服务器
    * 需要配置publish不是project，不可删掉
    * 发布至snapshot服务器需配置以下内容
    * 版本号以-SNAPSHOT结尾
    * 配置deploy.snapshot.url指向maven snapshot服务器地址
    * 配置deploy.snapshot.user.name指向maven snapshot服务器用户名
    * 配置deploy.snapshot.user.password指向maven snapshot服务器密码
    * 发布至release服务器需配置以下内容
    * 版本号不以-SNAPSHOT结尾
    * 配置deploy.release.url指向maven release服务器地址
    * 配置deploy.release.user.name指向maven release服务器用户名
    * 配置deploy.release.user.password指向maven release服务器密码
    */
    ext {
        groupId = 'com.example.myjetpackapplication'
        artifactId = 'business-${moduleName?lower_case}'
        version = project.extensions.android.defaultConfig.versionName
        description = 'test cases for ${moduleName?lower_case}'
    }

    apply from: "${rootDir}/maven-publish.gradle"
}
