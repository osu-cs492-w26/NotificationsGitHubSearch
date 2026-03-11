package edu.oregonstate.cs492.notificationsgithubsearch

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import edu.oregonstate.cs492.notificationsgithubsearch.data.AppDatabase
import edu.oregonstate.cs492.notificationsgithubsearch.data.BookmarkedReposRepository
import edu.oregonstate.cs492.notificationsgithubsearch.data.GitHubRepo
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
                sendNotification(apiRepo.getOrThrow())
                bookmarkedReposRepository.updateBookmarkedRepo(
                    apiRepo.getOrThrow()
                )
            }
        }
        return Result.success()
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun sendNotification(repo: GitHubRepo) {
        val builder = NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.notification_stars_channel)
        )

        val pendingIntent = NavDeepLinkBuilder(applicationContext)
            .setGraph(R.navigation.main_nav_graph)
            .setDestination(R.id.github_repo_detail)
            .setArguments(bundleOf("repo" to repo))
            .createPendingIntent()

        builder.setContentTitle(applicationContext.getString(
            R.string.notification_stars_title, repo.name
        ))
            .setContentText(applicationContext.getString(
                R.string.notification_stars_text, repo.name, repo.stars
            ))
            .setSmallIcon(R.drawable.ic_github_logo)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        NotificationManagerCompat.from(applicationContext)
            .notify(repo.name.hashCode(), builder.build())
    }
}