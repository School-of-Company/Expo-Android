package com.school_of_company.expo_android.di

import com.school_of_company.expo_android.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppConfigModule {

    @Provides
    @Named("NATIVE_APP_KEY")
    fun provideNativeAppKey(): String = BuildConfig.NATIVE_APP_KEY
}