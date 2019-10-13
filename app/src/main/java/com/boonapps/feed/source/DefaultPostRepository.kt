package com.boonapps.feed.source

import com.boonapps.feed.data.Post
import com.boonapps.feed.data.Result
import com.boonapps.feed.data.User
import com.boonapps.feed.source.local.LocalDataSource
import com.boonapps.feed.source.remote.RemoteDataSource
import com.boonapps.feed.util.wrapEspressoIdlingResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class DefaultPostRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localSource: LocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PostsRepository {

    override suspend fun getPosts(): Result<List<Post>> {

        wrapEspressoIdlingResource {

            return withContext(ioDispatcher) {

                // fetching users first is must because post has a foriegn with user
                val usersfetched = fetchUsersFromRemoteAndSaveToLocal()

                (usersfetched as? Result.Success)?.let {

                    val newPosts = fetchPostsFromRemoteOrLocal()


                    (newPosts as? Result.Success)?.let {
                        return@withContext newPosts
                    }

                    (newPosts as? Result.Success)?.let {
                        if (it.data.isEmpty()) {
                            return@withContext Result.Success(it.data)
                        }
                    }
                }

                return@withContext Result.Error(Exception("Illegal state"))
            }
        }
    }

    private suspend fun fetchUsersFromRemoteAndSaveToLocal(): Result<Unit> {
        val remoteUsers = remoteDataSource.getUsers()
        when (remoteUsers) {
            is Result.Error -> Timber.w("Remote data source fetch failed")
            is Result.Success -> {
                localSource.saveUsers(remoteUsers.data)
                return Result.Success(Unit)
            }
            else -> throw IllegalStateException()
        }

        return Result.Error(Exception("Illegal state"))
    }

    private suspend fun fetchPostsFromRemoteOrLocal(): Result<List<Post>> {
        // Remote first
        val remotePosts = remoteDataSource.getPosts()
        when (remotePosts) {
            is Result.Error -> Timber.w("Remote data source fetch failed")
            is Result.Success -> {
                refreshLocalDataSource(remotePosts.data)
                return remotePosts
            }
            else -> throw IllegalStateException()
        }

        // Local if remote fails
        val localPosts = localSource.getPosts()
        if (localPosts is Result.Success) return localPosts
        return Result.Error(Exception("Error fetching from remote and local"))
    }

    private suspend fun refreshLocalDataSource(posts: List<Post>) {
        for (post in posts) {
            localSource.savePost(post)
        }
    }


    override suspend fun getPost(postId: Int): Result<Post> {

        wrapEspressoIdlingResource {

            return withContext(ioDispatcher) {
                val newPost = fetchPostFromLocal(postId)
                return@withContext newPost
            }
        }
    }

    private suspend fun fetchPostFromLocal(postId: Int): Result<Post> {
        // Remote first
        val post = localSource.getPost(postId)
        when (post) {
            is Error -> Timber.w("Remote data source fetch failed")
            is Result.Success -> {

                return post
            }
            else -> throw IllegalStateException()
        }

        return Result.Error(Exception("Error fetching from remote and local"))
    }

    override suspend fun getUser(userId: Int): Result<User> {
        wrapEspressoIdlingResource {

            return withContext(ioDispatcher) {

                val user = fetchUserFromLocal(userId)

                return@withContext user
            }
        }
    }

    private suspend fun fetchUserFromLocal(userId: Int): Result<User> {
        val user = localSource.getUser(userId)
        when (user) {
            is Error -> Timber.w("Remote data source fetch failed")
            is Result.Success -> {
                return user
            }
            else -> throw IllegalStateException()
        }

        return Result.Error(Exception("Error fetching from remote and local"))
    }


    override suspend fun getCommentsCount(postId: Int): Result<Int> {

        wrapEspressoIdlingResource {

            return withContext(ioDispatcher) {

                when (val result = getCommentCountFromLocal(postId)) {
                    is Result.Error -> {
                        fetchCommentsFromRemoteAndSaveToLocal()
                        return@withContext getCommentCountFromLocal(postId)
                    }

                    is Result.Success -> {
                        return@withContext result
                    }
                }

                return@withContext Result.Error(Exception("Illegal state"))
            }
        }
    }

    private suspend fun fetchCommentsFromRemoteAndSaveToLocal(): Result<Unit> {
        val comments = remoteDataSource.getComments()
        when (comments) {
            is Result.Error -> Timber.w("Remote data source fetch failed")
            is Result.Success -> {
                localSource.saveComments(comments.data)
                return Result.Success(Unit)
            }
            else -> throw IllegalStateException()
        }

        return Result.Error(Exception("Illegal state"))
    }


    private suspend fun getCommentCountFromLocal(userId: Int): Result<Int> {
        val result = localSource.getCommentCount(userId)

        when (result) {
            is Error -> Timber.w("Remote data source fetch failed")
            is Result.Success -> {
                if (result.data != 0)
                    return result
            }
            else -> throw IllegalStateException()
        }

        return Result.Error(Exception("Error fetching from remote and local"))
    }
}