package edu.oregonstate.cs492.notificationsgithubsearch.data

import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Path

interface GitHubService {
    @GET("repos/{name}")
    suspend fun getRepo(
        @Path("name", encoded = true) name: String
    ) : GitHubRepo

    companion object {
        private val BASE_URL = "https://api.github.com/"

        // GitHubService.create()
        fun create(): GitHubService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(GitHubService::class.java)
        }
    }
}