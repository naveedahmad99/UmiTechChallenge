package com.urban.mobility.io.data.interfaces

import com.urban.mobility.io.domain.Repository
import com.urban.mobility.io.domain.RepositoryContainer
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubWebservice {

    @GET("search/repositories?sort=stars&order=desc&q=android")
    fun loadTrendingRepos(@Query("page") page: Int): Single<RepositoryContainer>

    @GET("repos/{username}/{repository}")
    fun loadRepoDetail(@Path("username") username: String, @Path("repository") repoName: String): Single<Repository>
}