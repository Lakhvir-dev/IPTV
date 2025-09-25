package com.dhiman.iptv.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.dhiman.iptv.BuildConfig
import com.dhiman.iptv.data.api.ApiHelper
import com.dhiman.iptv.data.api.ApiHelperImpl
import com.dhiman.iptv.data.api.ApiService
import com.dhiman.iptv.data.local.db.AppDatabase
import com.dhiman.iptv.data.local.prefs.SharedPrefs
import com.dhiman.iptv.util.ConstantUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .connectTimeout(2, TimeUnit.MINUTES)
        .writeTimeout(2, TimeUnit.MINUTES)
        .readTimeout(2, TimeUnit.MINUTES)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        baseUrl: String
    ): Retrofit {
        val retrofitBaseUrl = baseUrl.ifEmpty {
            "http://lume-dns.com:8080"
        }

        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(retrofitBaseUrl)
            .client(okHttpClient)
            .build()
    }


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun getBaseUrl(sharedPrefs: SharedPrefs): String {
        return sharedPrefs.getString(ConstantUtil.BASE_URL)
    }

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_db.db"
        ).build()
    }

    @Provides
    @Singleton
    fun providerSharedPref(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            ConstantUtil.SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

}