package edu.oregonstate.cs492.notificationsgithubsearch

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class GitHubSearchApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        launchBookmarksSyncWorker()
    }

    private fun launchBookmarksSyncWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest = PeriodicWorkRequestBuilder<BookmarksSyncWorker>(
            12,
            TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "bookmarksSyncWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}