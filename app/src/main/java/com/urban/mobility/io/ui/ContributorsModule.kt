package com.urban.mobility.io.ui

import com.urban.mobility.io.data.TrendingReposRepositoryModule
import com.urban.mobility.io.ui.trendings.TrendingReposFragment
import com.urban.mobility.io.ui.trendings.TrendingReposModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ContributorsModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [TrendingReposRepositoryModule::class, TrendingReposModule::class])
    abstract fun bindTrendingReposFragment(): TrendingReposFragment
}