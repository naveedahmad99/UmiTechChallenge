package com.urban.mobility.io.data.modules

import com.urban.mobility.io.data.API_REST_URL
import com.urban.mobility.io.data.NAMED_REST_API_URL
import com.urban.mobility.io.data.SchedulersProvider
import com.urban.mobility.io.data.interfaces.ISchedulersProvider
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    @Named(NAMED_REST_API_URL)
    fun provideRestApiUrl(): String = API_REST_URL

    @Provides
    fun provideSchedulersProvider(): ISchedulersProvider =
        SchedulersProvider()

}