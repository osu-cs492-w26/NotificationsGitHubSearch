package edu.oregonstate.cs492.notificationsgithubsearch.data

class BookmarkedReposRepository(
    private val dao: GitHubRepoDao
) {
    fun getAllBookmarkedRepos() = dao.getAllRepos()
}