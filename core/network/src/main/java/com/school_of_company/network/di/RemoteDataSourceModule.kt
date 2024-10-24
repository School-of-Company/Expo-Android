package com.school_of_company.network.di

import com.school_of_company.network.datasource.auth.AuthDataSource
import com.school_of_company.network.datasource.auth.AuthDataSourceImpl
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
}