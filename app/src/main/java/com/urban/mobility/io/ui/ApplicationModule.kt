package com.urban.mobility.io.ui

import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    fun provideActionManager(): ActionManager = ActionManager.instance
}