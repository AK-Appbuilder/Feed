package com.boonapps.feed.source.local

import com.boonapps.feed.data.Comment
import com.boonapps.feed.data.Post
import com.boonapps.feed.data.Result
import com.boonapps.feed.data.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataSource internal constructor(private val postDao: PostDao,
                                           private val userDao: UserDao,
                                           private val commentDao: CommentDao,
                                           private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) {

     suspend fun getPosts() =  withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(postDao.getPosts())
        } catch (e: Exception) {
            Error(e)
        }
    }  as Result<List<Post>>


     suspend fun getPost(postId: Int): Result<Post> = withContext(ioDispatcher) {
        try {
            val post = postDao.getPostById(postId)
            if (post != null) {
                return@withContext Result.Success(post)
            } else {
                return@withContext Error(Exception("Post not found!"))
            }
        } catch (e: Exception) {
            return@withContext Error(e)
        }
    } as Result<Post>


     suspend fun savePost(post: Post) = withContext(ioDispatcher) {
        postDao.insertPost(post)
    }

    suspend fun getUser(userId: Int): Result<User> = withContext(ioDispatcher) {
        try {
            val user = userDao.getUserById(userId)
            if (user != null) {
                return@withContext Result.Success(user)
            } else {
                return@withContext Error(Exception("Post not found!"))
            }
        } catch (e: Exception) {
            return@withContext Error(e)
        }
    } as Result<User>

    suspend fun saveUsers(users: List<User>) = withContext(ioDispatcher){
        userDao.insertUsers(users)
    }

    suspend fun saveComments(comments: List<Comment>) = withContext(ioDispatcher) {
        commentDao.insertComments(comments)
    }

    suspend fun getCommentCount(postId: Int): Result<Int> = withContext(ioDispatcher) {
        try {
            val count = commentDao.getCommentsCount(postId)
            if (count != null) {
                return@withContext Result.Success(count)
            } else {
                return@withContext Error(Exception("Post not found!"))
            }
        } catch (e: Exception) {
            return@withContext Error(e)
        }
    } as Result<Int>

}