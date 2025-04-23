package com.school_of_company.expo_android

import android.app.Application
import com.kakao.vectormap.KakaoMapSdk
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidApp
class ExpoApplication : Application() {

    @Inject
    @Named("NATIVE_APP_KEY")
    lateinit var nativeAppKey: String

    override fun onCreate() {
        super.onCreate()

        KakaoMapSdk.init(this, nativeAppKey)
    }
}