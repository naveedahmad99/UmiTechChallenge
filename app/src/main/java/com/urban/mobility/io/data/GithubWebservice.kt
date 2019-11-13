package com.urban.mobility.io.data

import com.urban.mobility.io.domain.RepositoryContainer
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubWebservice {

    @GET("search/repositories?sort=stars&order=desc&q=android")
    fun loadTrendingRepos(@Query("page") page: Int): Single<RepositoryContainer>
}