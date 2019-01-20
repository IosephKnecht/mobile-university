package com.project.mobile_university.application.assembly

import android.content.Context
import com.google.gson.Gson
import com.project.mobile_university.application.annotations.PerBusinessLayerScope
import com.project.mobile_university.domain.ApiService
import com.project.mobile_university.domain.SharedPreferenceService
import com.project.mobile_university.domain.UniversityApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class BusinessModule {
    @Provides
    @PerBusinessLayerScope
    fun provideApiService(sharePrefService: SharedPreferenceService): ApiService {
        return ApiService(sharePrefService)
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
        return Gson()
    }
}