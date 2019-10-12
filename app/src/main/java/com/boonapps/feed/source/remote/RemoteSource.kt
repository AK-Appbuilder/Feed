package com.boonapps.feed.source.remote

import com.boonapps.feed.data.Post
import com.boonapps.feed.data.Result
import com.boonapps.feed.source.DataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteSource internal constructor(
    private val service: ApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    DataSource {

    override suspend fun getPosts(): Result<List<Post>> = withContext(ioDispatcher) {
        return@withContext try {

            val response = service.getPosts()
            if (response.isSuccessful) {
                Result.Success(response.body())
            } else {
                Result.Error(Exception(response.message()))
            }

        } catch (e: Exception) {
            Error(e)
        }
    } as Result<List<Post>>


    override suspend fun getPost(taskId: Int): Result<Post> {
        return Result.Error(java.lang.Exception("no post found"))
    }
}