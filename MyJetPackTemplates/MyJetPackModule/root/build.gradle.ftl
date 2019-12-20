apply plugin: 'com.android.library'
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

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
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
    api "com.github.seelikes.android:mvvm-basic:$mvvm_basic_version"
    api "com.java.lib:oil:$oil_version"
}
