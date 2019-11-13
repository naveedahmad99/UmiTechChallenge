package com.urban.mobility.io.domain

import io.reactivex.Single

interface RepositoryContract {
    interface ITrendingReposRepository {
        fun loadTrendingRepos(page: Int): Single<ReposContainer>
    }
}