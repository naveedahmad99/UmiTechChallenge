package com.urban.mobility.io.data

import com.urban.mobility.io.domain.RepositoryContract
import dagger.Module
import dagger.Provides

@Module
class TrendingReposRepositoryModule {
    @Provides
    fun provideTrendingReposRepository(repository: TrendingReposRepository):
            RepositoryContract.ITrendingReposRepository = repository
}