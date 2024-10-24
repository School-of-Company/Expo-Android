package com.school_of_company.network.di

import android.content.Context
import android.util.Log
import com.readystatesoftware.chuck.ChuckInterceptor
import com.school_of_company.network.BuildConfig
import com.school_of_company.network.api.AuthAPI
import com.school_of_company.network.util.AuthInterceptor
import com.school_of_company.network.util.TokenAuthenticator
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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
    fun provideHttpLoggingInterceptor() : HttpLoggingInterceptor =
        HttpLoggingInterceptor { message -> Log.v("Http", message) }
            .setLevel(HttpLoggingInterceptor.Level.BASIC)

    @Provides
    @Singleton
    fun provideHttpClient(
        @ApplicationContext context: Context,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator,
    ) : OkHttpClient {
        return OkHttpClient.Builder()
            .cookieJar(CookieJar.NO_COOKIES)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authInterceptor)
            .addInterceptor(
                if (BuildConfig.DEBUG) {
                    ChuckInterceptor(context)
                        .showNotification(true)
                        .maxContentLength(250000)
                        .retainDataFor(ChuckInterceptor.Period.ONE_DAY)
                } else {
                    Interceptor {chain -> chain.proceed(chain.request()) }
                }
            )
            .authenticator(tokenAuthenticator)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshiInstance() : Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(moshi: Moshi) : MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Provides
    fun provideAuthAPI(retrofit: Retrofit) : AuthAPI =
        retrofit.create(AuthAPI::class.java)
}