package com.urban.mobility.io.data

import com.urban.mobility.io.data.interfaces.GithubWebservice
import com.urban.mobility.io.data.interfaces.ISchedulersProvider
import com.urban.mobility.io.domain.Repository
import com.urban.mobility.io.domain.RepositoryContract
import io.reactivex.Single
import javax.inject.Inject

class RepoDetailRepository @Inject constructor(
    private val webservice: GithubWebservice,
    private val schedulersProvider: ISchedulersProvider
) : RepositoryContract.IRepoDetailReposRepository {
    override fun loadRepoDetail(ownerName: String, repoName: String): Single<Repository> =
        webservice
            .loadRepoDetail(ownerName, repoName)
            .subscribeOn(schedulersProvider.io())

}