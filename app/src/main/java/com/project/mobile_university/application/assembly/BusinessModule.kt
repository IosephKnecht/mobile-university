package com.project.mobile_university.application.assembly

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.project.mobile_university.BuildConfig
import com.project.mobile_university.application.annotations.PerBusinessLayerScope
import com.project.mobile_university.data.gson.Teacher
import com.project.mobile_university.data.gson.User
import com.project.mobile_university.domain.UniversityApi
import com.project.mobile_university.domain.UniversityDatabase
import com.project.mobile_university.domain.adapters.exception.ExceptionAdapter
import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
import com.project.mobile_university.domain.adapters.exception.RetrofitExceptionMatcher
import com.project.mobile_university.domain.adapters.gson.TeacherAdapter
import com.project.mobile_university.domain.adapters.gson.UserAdapter
import com.project.mobile_university.domain.interceptors.LogJsonInterceptor
import com.project.mobile_university.domain.repository.LoginRepositoryImpl
import com.project.mobile_university.domain.repository.LoginRepositoryMock
import com.project.mobile_university.domain.repository.ScheduleRepositoryImpl
import com.project.mobile_university.domain.repository.ScheduleRepositoryMock
import com.project.mobile_university.domain.services.ApiServiceImpl
import com.project.mobile_university.domain.services.DatabaseServiceImpl
import com.project.mobile_university.domain.services.SharedPreferenceServiceImpl
import com.project.mobile_university.domain.shared.*
import com.project.mobile_university.presentation.createUniversityApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class BusinessModule {

    @Provides
    fun provideUniversityApi(
        okHttpClient: OkHttpClient,
        gson: Gson,
        retrofitExceptionAdapter: ExceptionAdapter,
        sharedPreferenceService: SharedPreferenceService
    ): UniversityApi {
        return createUniversityApi(
            httpClient = okHttpClient,
            gson = gson,
            retrofitExceptionAdapter = retrofitExceptionAdapter,
            serviceUrl = sharedPreferenceService.getServerConfig().toString()
        )
    }


    @Provides
    @PerBusinessLayerScope
    fun provideApiService(
        sharePrefService: SharedPreferenceService,
        gson: Gson,
        okHttpClient: OkHttpClient,
        retrofitExceptionMatcher: ExceptionAdapter,
        universityApi: UniversityApi
    ): ApiService {
        return ApiServiceImpl(sharePrefService, gson, okHttpClient, retrofitExceptionMatcher, universityApi)
    }

    @Provides
    @PerBusinessLayerScope
    fun provideSharedPrefService(
        context: Context,
        gson: Gson
    ): SharedPreferenceService {
        return SharedPreferenceServiceImpl(context, gson)
    }

    @Provides
    @PerBusinessLayerScope
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(User::class.java, UserAdapter())
            .registerTypeAdapter(Teacher::class.java, TeacherAdapter())
            .create()
    }

    @Provides
    @PerBusinessLayerScope
    fun provideDatabase(context: Context): UniversityDatabase {
        return Room.databaseBuilder(
            context,
            UniversityDatabase::class.java,
            "university_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @PerBusinessLayerScope
    fun provideDatabaseService(database: UniversityDatabase): DatabaseService {
        return DatabaseServiceImpl(database)
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
    @PerBusinessLayerScope
    fun provideExceptionConverter(context: Context): ExceptionConverter {
        return ExceptionConverter(context)
    }

    @Provides
    @PerBusinessLayerScope
    fun provideScheduleRepository(
        apiService: ApiService,
        databaseService: DatabaseService,
        sharePrefService: SharedPreferenceService
    ): ScheduleRepository {
        return if (BuildConfig.MOCK_SETTINGS) {
            ScheduleRepositoryMock()
        } else {
            ScheduleRepositoryImpl(apiService, databaseService, sharePrefService)
        }
    }

    @Provides
    @PerBusinessLayerScope
    fun provideLoginRepository(
        apiService: ApiService,
        sharedPreferenceService: SharedPreferenceService
    ): LoginRepository {
        return if (BuildConfig.MOCK_SETTINGS) {
            LoginRepositoryMock(sharedPreferenceService)
        } else {
            LoginRepositoryImpl(apiService, sharedPreferenceService)
        }
    }
}