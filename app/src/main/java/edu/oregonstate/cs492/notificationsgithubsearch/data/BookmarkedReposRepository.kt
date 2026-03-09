package edu.oregonstate.cs492.notificationsgithubsearch.data

class BookmarkedReposRepository(
    private val dao: GitHubRepoDao
) {
    fun getAllBookmarkedRepos() = dao.getAllRepos()
    suspend fun getAllBookmarkedReposOnce() = dao.getAllReposOnce()
    suspend fun updateBookmarkedRepo(repo: GitHubRepo) = dao.update(repo)
}