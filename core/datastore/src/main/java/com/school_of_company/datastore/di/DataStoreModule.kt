package com.school_of_company.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.school_of_company.datastore.AuthToken
import com.school_of_company.datastore.serializer.AuthTokenSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideAuthTokenDataSource(
        @ApplicationContext context: Context,
        authTokenSerializer: AuthTokenSerializer
    ) : DataStore<AuthToken> =
        // DataStoreFactory를 사용하여 DataStore을 생성하고 반환합니다.
        DataStoreFactory.create(
            // 생성된 DataStore에서 사용할 직렬화 객체를 지정합니다.
            serializer = authTokenSerializer
        ) {
            // dataStoreFile 메서드를 사용하여 앱의 기본 저장소 위치에 "authToken.pd"라는 파일명으로 저장이 됩니다.
            context.dataStoreFile("authToken.pd")
        }
}