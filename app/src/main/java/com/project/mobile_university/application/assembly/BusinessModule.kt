package com.project.mobile_university.application.assembly

import com.project.mobile_university.application.annotations.PerBusinessLayerScope
import com.project.mobile_university.domain.ApiService
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
    fun provideRetorofit(): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @PerBusinessLayerScope
    fun provideUniversityApi(retrofit: Retrofit): UniversityApi {
        return retrofit.create(UniversityApi::class.java)
    }

    @Provides
    @PerBusinessLayerScope
    fun provideApiService(universityApi: UniversityApi): ApiService {
        return ApiService(universityApi)
    }
}