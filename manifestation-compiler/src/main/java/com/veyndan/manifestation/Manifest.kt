package com.veyndan.manifestation

class Manifest {
    fun render(): String {
        return """
            <?xml version="1.0" encoding="utf-8"?>
            <manifest
                xmlns:android="http://schemas.android.com/apk/res/android"
                package="com.veyndan.manifestation">

                <application
                    android:allowBackup="true"
                    android:icon="@mipmap/ic_launcher"
                    android:label="@string/app_name"
                    android:roundIcon="@mipmap/ic_launcher_round"
                    android:supportsRtl="true"
                    android:theme="@style/AppTheme">

                    <activity android:name=".MainActivity">

                        <intent-filter>

                            <action android:name="android.intent.action.MAIN" />

                            <category android:name="android.intent.category.LAUNCHER" />

                        </intent-filter>

                    </activity>

                </application>

            </manifest>

            """.trimIndent()
    }
}

fun manifest(init: Manifest.() -> Unit) = Manifest().apply(init)
