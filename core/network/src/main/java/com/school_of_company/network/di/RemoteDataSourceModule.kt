package com.school_of_company.network.di

import com.school_of_company.network.datasource.auth.AuthDataSource
import com.school_of_company.network.datasource.auth.AuthDataSourceImpl
import com.school_of_company.network.datasource.expo.ExpoDataSource
import com.school_of_company.network.datasource.expo.ExpoDataSourceImpl
import com.school_of_company.network.datasource.image.ImageDataSource
import com.school_of_company.network.datasource.image.ImageDataSourceImpl
import com.school_of_company.network.datasource.sms.SmsDataSource
import com.school_of_company.network.datasource.sms.SmsDataSourceImpl
import com.school_of_company.network.datasource.standard.StandardDataSource
import com.school_of_company.network.datasource.standard.StandardDataSourceImpl
import com.school_of_company.network.datasource.training.TrainingDataSource
import com.school_of_company.network.datasource.training.TrainingDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun bindAuthRemoteDataSource(
        authDataSourceImpl: AuthDataSourceImpl
    ) : AuthDataSource

    @Binds
    abstract fun bindSmsRemoteDataSource(
        smsDataSourceImpl: SmsDataSourceImpl
    ) : SmsDataSource

    @Binds
    abstract fun bindExpoRemoteDataSource(
        expoDataSourceImpl: ExpoDataSourceImpl
    ) : ExpoDataSource

    @Binds
    abstract fun bindImageRemoteDataSource(
        imageDataSourceImpl: ImageDataSourceImpl
    ) : ImageDataSource

    @Binds
    abstract fun bindTrainingRemoteDataSource(
        trainingDataSourceImpl: TrainingDataSourceImpl
    ) : TrainingDataSource

    @Binds
    abstract fun bindStandardRemoteDataSource(
        standardDataSourceImpl: StandardDataSourceImpl
    ) : StandardDataSource
}