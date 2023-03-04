package com.testapp.di

import com.testapp.BuildConfig
import com.testapp.data.networking.AppServices
import com.testapp.data.networking.interceptor.AuthInterceptor

import com.testapp.data.networking.interceptor.ConnectivityInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthOkHttpClient(
        authInterceptor: AuthInterceptor,
        connectivityInterceptor: ConnectivityInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        //Set desired log level
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient()
            .newBuilder().connectTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor(connectivityInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideAppApi(retrofit: Retrofit): AppServices = retrofit.create(AppServices::class.java)

}