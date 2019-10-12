package com.boonapps.feed.source.local

import com.boonapps.feed.data.Post
import com.boonapps.feed.data.Result
import com.boonapps.feed.source.DataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalSource internal constructor(private val postDao: PostDao,
                                       private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) : DataSource{

    override suspend fun getPosts() =  withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(postDao.getPosts())
        } catch (e: Exception) {
            Error(e)
        }
    }  as Result<List<Post>>


    override suspend fun getPost(postId: Int): Result<Post> = withContext(ioDispatcher) {
        try {
            val task = postDao.getPostById(postId)
            if (task != null) {
                return@withContext Result.Success(task)
            } else {
                return@withContext Error(Exception("Post not found!"))
            }
        } catch (e: Exception) {
            return@withContext Error(e)
        }
    } as Result<Post>




}