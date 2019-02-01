package com.project.mobile_university.application.assembly

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.project.mobile_university.application.annotations.PerBusinessLayerScope
import com.project.mobile_university.data.gson.User
import com.project.mobile_university.domain.ApiService
import com.project.mobile_university.domain.DatabaseService
import com.project.mobile_university.domain.SharedPreferenceService
import com.project.mobile_university.domain.UniversityDatabase
import com.project.mobile_university.domain.adapters.exception.ExceptionAdapter
import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
import com.project.mobile_university.domain.adapters.exception.RetrofitExceptionMatcher
import com.project.mobile_university.domain.adapters.gson.UserAdapter
import com.project.mobile_university.domain.interceptors.LogJsonInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class BusinessModule {
    @Provides
    @PerBusinessLayerScope
    fun provideApiService(sharePrefService: SharedPreferenceService,
                          gson: Gson,
                          okHttpClient: OkHttpClient,
                          retrofitExceptionMatcher: ExceptionAdapter): ApiService {
        return ApiService(sharePrefService, gson, okHttpClient, retrofitExceptionMatcher)
    }

    @Provides
    @PerBusinessLayerScope
    fun provideSharedPrefService(context: Context,
                                 gson: Gson): SharedPreferenceService {
        return SharedPreferenceService(context, gson)
    }

    @Provides
    @PerBusinessLayerScope
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(User::class.java, UserAdapter())
            .create()
    }

    @Provides
    @PerBusinessLayerScope
    fun provideDatabase(context: Context): UniversityDatabase {
        return Room.databaseBuilder(context,
            UniversityDatabase::class.java,
            "university_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @PerBusinessLayerScope
    fun provideDatabaseService(database: UniversityDatabase): DatabaseService {
        return DatabaseService(database)
    }

    @Provides
    @PerBusinessLayerScope
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(LogJsonInterceptor())
            .build()
    }

    @Provides
    @PerBusinessLayerScope
    fun provideRetrofitExceptionAdapter(): ExceptionAdapter {
        return RetrofitExceptionMatcher()
    }

    @Provides
    fun provideExceptionConverter(context: Context): ExceptionConverter {
        return ExceptionConverter(context)
    }
}