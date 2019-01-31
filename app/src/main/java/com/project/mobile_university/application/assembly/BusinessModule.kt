package com.project.mobile_university.application.assembly

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.project.mobile_university.application.annotations.PerBusinessLayerScope
import com.project.mobile_university.data.gson.User
import com.project.mobile_university.domain.ApiService
import com.project.mobile_university.domain.SharedPreferenceService
import com.project.mobile_university.domain.adapters.UserAdapter
import dagger.Module
import dagger.Provides

@Module
class BusinessModule {
    @Provides
    @PerBusinessLayerScope
    fun provideApiService(sharePrefService: SharedPreferenceService,
                          gson: Gson): ApiService {
        return ApiService(sharePrefService, gson)
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
}