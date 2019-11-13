package com.urban.mobility.io.ui.trendings

import com.urban.mobility.io.data.ISchedulersProvider
import com.urban.mobility.io.domain.RepositoryContract
import dagger.Module
import dagger.Provides

@Module
class TrendingReposModule {

    @Provides
    fun provideTrendingReposViewModelFactory(repository: RepositoryContract.ITrendingReposRepository, schedulers: ISchedulersProvider) =
            TrendingReposViewModelFactory(repository, schedulers)
}