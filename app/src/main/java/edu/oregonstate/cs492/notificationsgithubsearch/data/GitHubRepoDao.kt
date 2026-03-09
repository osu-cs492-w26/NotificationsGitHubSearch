package edu.oregonstate.cs492.notificationsgithubsearch.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GitHubRepoDao {
    @Insert
    suspend fun insert(repo: GitHubRepo)

    @Delete
    suspend fun delete(repo: GitHubRepo)

    @Update
    suspend fun update(repo: GitHubRepo)

    @Query("SELECT * FROM GitHubRepo")
    fun getAllRepos(): Flow<List<GitHubRepo>>

    @Query("SELECT * FROM GitHubRepo")
    suspend fun getAllReposOnce(): List<GitHubRepo>

    @Query("SELECT * FROM GitHubRepo WHERE name = :name LIMIT 1")
    fun getRepoByName(name: String): Flow<GitHubRepo?>
}