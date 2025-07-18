package com.nativedevps.myapplication.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.nativedevps.data.repositories.remote.usecase.app_authorize.AppAuthorizeUseCase
import com.nativedevps.domain.datasources.local.IDataStorePreferenceDataSource
import com.nativedevps.myapplication.domain.datasource.rest.UserApiService
import com.nativedevps.myapplication.utility.RemoteConfigurationListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.digikraft.data.utility.ResultCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object ApiServiceModule {

    @Provides
    fun provideRetrofitClient(
        @ApplicationContext appContext: Context
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .addInterceptor(ChuckerInterceptor.Builder(appContext).build())
            .readTimeout(180, TimeUnit.SECONDS)
            .connectTimeout(180, TimeUnit.SECONDS).build()

    @Provides
    @Singleton
    fun provideAuthApiService(@OpenExchangeRetrofit retrofit: Retrofit): UserApiService =
        retrofit.create(
            UserApiService::class.java
        )

    @Provides
    @Singleton
    @OpenExchangeRetrofit
    fun provideOpenExchangeRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(ResultCallAdapterFactory())
        .baseUrl("https://openexchangerates.org/api/")
        .build()
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class OpenExchangeRetrofit