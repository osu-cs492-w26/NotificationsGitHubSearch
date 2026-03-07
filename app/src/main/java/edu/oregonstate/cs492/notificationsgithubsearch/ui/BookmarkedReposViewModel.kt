package edu.oregonstate.cs492.notificationsgithubsearch.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import edu.oregonstate.cs492.notificationsgithubsearch.data.AppDatabase
import edu.oregonstate.cs492.notificationsgithubsearch.data.BookmarkedReposRepository

class BookmarkedReposViewModel(application: Application) :
    AndroidViewModel(application)
{
    private val repository = BookmarkedReposRepository(
        AppDatabase.getInstance(application).gitHubRepoDao()
    )

    val bookmarkedRepos = repository.getAllBookmarkedRepos().asLiveData()
}