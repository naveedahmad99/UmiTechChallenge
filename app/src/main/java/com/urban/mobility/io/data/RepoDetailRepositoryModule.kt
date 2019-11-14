package com.urban.mobility.io.data

import com.urban.mobility.io.domain.RepositoryContract
import dagger.Module
import dagger.Provides

@Module
class RepoDetailRepositoryModule {
    @Provides
    fun provideRepoDetailRepository(repository: RepoDetailRepository):
            RepositoryContract.IRepoDetailReposRepository = repository

}