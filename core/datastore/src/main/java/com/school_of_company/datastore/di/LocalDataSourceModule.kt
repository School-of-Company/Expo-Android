package com.school_of_company.datastore.di

import com.school_of_company.datastore.datasource.AuthTokenDataSource
import com.school_of_company.datastore.datasource.AuthTokenDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun bindAuthTokenDataSource(
        authTokenDataSourceImpl: AuthTokenDataSourceImpl
    ): AuthTokenDataSource
}