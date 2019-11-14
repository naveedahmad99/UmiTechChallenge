package com.urban.mobility.io.ui.repo

import com.urban.mobility.io.data.interfaces.ISchedulersProvider
import com.urban.mobility.io.domain.RepositoryContract
import dagger.Module
import dagger.Provides

@Module
class RepoDetailModule {

    @Provides
    fun provideTrendingReposViewModelFactory(
        repository: RepositoryContract.IRepoDetailReposRepository,
        schedulers: ISchedulersProvider
    ) =
        RepoDetailViewModelFactory(repository, schedulers)
}