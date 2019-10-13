package com.boonapps.feed.source.remote

import com.boonapps.feed.data.Comment
import com.boonapps.feed.data.Post
import com.boonapps.feed.data.Result
import com.boonapps.feed.data.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val service: ApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getPosts(): Result<List<Post>> = withContext(ioDispatcher) {
        return@withContext try {

            val response = service.getPosts()
            if (response.isSuccessful) {
                Result.Success(response.body())
            } else {
                Timber.e(response.message())
                Result.Error(Exception(response.message()))
            }

        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(e)
        }
    } as Result<List<Post>>

    suspend fun getUsers(): Result<List<User>> = withContext(ioDispatcher) {
        return@withContext try {

            val response = service.getUsers()
            if (response.isSuccessful) {
                Result.Success(response.body())
            } else {
                Timber.e(response.message())
                Result.Error(Exception(response.message()))
            }

        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(e)
        }
    } as Result<List<User>>

    suspend fun getComments(): Result<List<Comment>> = withContext(ioDispatcher) {
        return@withContext try {

            val response = service.getComments()
            if (response.isSuccessful) {
                Result.Success(response.body())
            } else {
                Timber.e(response.message())
                Result.Error(Exception(response.message()))
            }

        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(e)
        }
    } as Result<List<Comment>>

}