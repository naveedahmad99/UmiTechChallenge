package com.urban.mobility.io

import com.urban.mobility.io.data.ApiServiceModule
import com.urban.mobility.io.data.ApiModule
import com.urban.mobility.io.ui.ApplicationModule
import com.urban.mobility.io.ui.ContributorsModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApiServiceModule::class,
    ApiModule::class,
    ApplicationModule::class,
    ContributorsModule::class
])
interface ApplicationComponent : AndroidInjector<UMIApplication> {


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: UMIApplication): Builder

        fun build(): ApplicationComponent
    }

}