package com.school_of_company.network.di

import android.content.Context
import android.util.Log
import com.readystatesoftware.chuck.ChuckInterceptor
import com.school_of_company.network.BuildConfig
import com.school_of_company.network.api.AddressAPI
import com.school_of_company.network.api.AdminAPI
import com.school_of_company.network.api.AttendanceAPI
import com.school_of_company.network.api.AuthAPI
import com.school_of_company.network.api.ExpoAPI
import com.school_of_company.network.api.FormAPI
import com.school_of_company.network.api.ImageAPI
import com.school_of_company.network.api.KakaoAPI
import com.school_of_company.network.api.ParticipantAPI
import com.school_of_company.network.api.SmsAPI
import com.school_of_company.network.api.StandardAPI
import com.school_of_company.network.api.TraineeAPI
import com.school_of_company.network.api.TrainingAPI
import com.school_of_company.network.util.AddressUrl
import com.school_of_company.network.util.AuthInterceptor
import com.school_of_company.network.util.BaseUrl
import com.school_of_company.network.util.KakaoUrl
import com.school_of_company.network.util.TokenAuthenticator
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.CookieJar
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor { message -> Log.v("Http", message) }
            .setLevel(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            )

    @Provides
    @Singleton
    fun provideHttpClient(
        @ApplicationContext context: Context,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cookieJar(CookieJar.NO_COOKIES)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authInterceptor)
            .authenticator(tokenAuthenticator)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshiInstance(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseApiRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    @KakaoUrl
    fun provideKakaoApiRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.KAKAO_URL)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    @AddressUrl
    fun provideAddressApiRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.ADDRESS_URL)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Provides
    fun provideAuthAPI(@BaseUrl retrofit: Retrofit): AuthAPI =
        retrofit.create(AuthAPI::class.java)

    @Provides
    fun provideSmsAPI(@BaseUrl retrofit: Retrofit): SmsAPI =
        retrofit.create(SmsAPI::class.java)

    @Provides
    fun provideExpoAPI(@BaseUrl retrofit: Retrofit): ExpoAPI =
        retrofit.create(ExpoAPI::class.java)

    @Provides
    fun provideImageAPI(@BaseUrl retrofit: Retrofit): ImageAPI =
        retrofit.create(ImageAPI::class.java)

    @Provides
    fun provideTrainingAPI(@BaseUrl retrofit: Retrofit): TrainingAPI =
        retrofit.create(TrainingAPI::class.java)

    @Provides
    fun provideStandardAPI(@BaseUrl retrofit: Retrofit): StandardAPI =
        retrofit.create(StandardAPI::class.java)

    @Provides
    fun provideAttendanceAPI(@BaseUrl retrofit: Retrofit): AttendanceAPI =
        retrofit.create(AttendanceAPI::class.java)

    @Provides
    fun provideAdminAPI(@BaseUrl retrofit: Retrofit): AdminAPI =
        retrofit.create(AdminAPI::class.java)

    @Provides
    fun provideTraineeAPI(@BaseUrl retrofit: Retrofit): TraineeAPI =
        retrofit.create(TraineeAPI::class.java)

    @Provides
    fun provideParticipantAPI(@BaseUrl retrofit: Retrofit): ParticipantAPI =
        retrofit.create(ParticipantAPI::class.java)

    @Provides
    fun provideFormAPI(@BaseUrl retrofit: Retrofit): FormAPI =
        retrofit.create(FormAPI::class.java)

    @Provides
    fun provideKakaoAPI(@KakaoUrl retrofit: Retrofit): KakaoAPI =
        retrofit.create(KakaoAPI::class.java)

    @Provides
    fun provideAddressAPI(@AddressUrl retrofit: Retrofit): AddressAPI =
        retrofit.create(AddressAPI::class.java)
}