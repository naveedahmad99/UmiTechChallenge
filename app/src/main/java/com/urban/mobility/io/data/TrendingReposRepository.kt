package com.urban.mobility.io.data

import com.urban.mobility.io.domain.ReposContainer
import com.urban.mobility.io.domain.RepositoryContract
import io.reactivex.Single
import javax.inject.Inject

class TrendingReposRepository @Inject constructor(private val webservice: GithubWebservice,
                                                      private val schedulersProvider: ISchedulersProvider)
    : RepositoryContract.ITrendingReposRepository {
    override fun loadTrendingRepos(page: Int): Single<ReposContainer> =
            webservice
                    .loadTrendingRepos(page)
                    .subscribeOn(schedulersProvider.io())
}