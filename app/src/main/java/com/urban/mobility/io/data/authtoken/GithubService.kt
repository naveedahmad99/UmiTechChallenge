package com.example.kyle.githubapi.api

import com.example.kyle.githubapi.login.AccessToken
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GithubService {

    private val client: GithubClient

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        client = retrofit.create(GithubClient::class.java)
    }

    fun getAccessToken(clientId: String, clientSecret: String, code: String): Call<AccessToken> {
        return client.getAccessToken(clientId, clientSecret, code)
    }
}