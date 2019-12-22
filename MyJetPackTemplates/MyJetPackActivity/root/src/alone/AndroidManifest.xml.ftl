<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <application>
    <#if isLauncher>
        <activity android:name="${packageName}.${activityClass}">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <action android:name="android.intent.action.VIEW" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    <#else>
        <activity android:name="${packageName}.${activityClass}" />
    </#if>
    </application>
</manifest>
