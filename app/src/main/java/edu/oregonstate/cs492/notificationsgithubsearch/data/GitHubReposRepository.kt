package edu.oregonstate.cs492.notificationsgithubsearch.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GitHubReposRepository(
    private val service: GitHubService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun loadRepo(name: String) : Result<GitHubRepo> =
        withContext(ioDispatcher) {
            try {
                val repo = service.getRepo(name)
                Result.success(repo)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}