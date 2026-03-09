package edu.oregonstate.cs492.notificationsgithubsearch

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import edu.oregonstate.cs492.notificationsgithubsearch.data.AppDatabase
import edu.oregonstate.cs492.notificationsgithubsearch.data.BookmarkedReposRepository
import edu.oregonstate.cs492.notificationsgithubsearch.data.GitHubReposRepository
import edu.oregonstate.cs492.notificationsgithubsearch.data.GitHubService

class BookmarksSyncWorker(
    context: Context,
    parameters: WorkerParameters
) : CoroutineWorker(context, parameters) {
    private val bookmarkedReposRepository = BookmarkedReposRepository(
        AppDatabase.getInstance(context).gitHubRepoDao()
    )
    private val githubReposRepository = GitHubReposRepository(
        GitHubService.create()
    )

    override suspend fun doWork(): Result {
        Log.d("BookmarksSyncWorker", "Work request running")
        val bookmarkedRepos = bookmarkedReposRepository.getAllBookmarkedReposOnce()
        for (repo in bookmarkedRepos) {
            val apiRepo = githubReposRepository.loadRepo(repo.name)
            if (apiRepo.isSuccess) {
                bookmarkedReposRepository.updateBookmarkedRepo(
                    apiRepo.getOrThrow()
                )
            }
        }
        return Result.success()
    }
}