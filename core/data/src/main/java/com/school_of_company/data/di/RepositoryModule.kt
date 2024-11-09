package com.school_of_company.data.di

import com.school_of_company.data.repository.auth.AuthRepository
import com.school_of_company.data.repository.auth.AuthRepositoryImpl
import com.school_of_company.data.repository.expo.ExpoRepository
import com.school_of_company.data.repository.expo.ExpoRepositoryImpl
import com.school_of_company.data.repository.sms.SmsRepository
import com.school_of_company.data.repository.sms.SmsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ) : AuthRepository

    @Binds
    abstract fun bindSmsRepository(
        smsRepositoryImpl: SmsRepositoryImpl
    ) : SmsRepository

    @Binds
    abstract fun bindExpoRepository(
        expoRepositoryImpl: ExpoRepositoryImpl
    ) : ExpoRepository

}