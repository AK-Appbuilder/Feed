package com.boonapps.feed.source

import com.boonapps.feed.data.Post
import com.boonapps.feed.data.Result
import com.boonapps.feed.data.User

interface PostsRepository {

    suspend fun getPosts() : Result<List<Post>>

    suspend fun getPost(postId: Int): Result<Post>

    suspend fun getUser(userId: Int): Result<User>

    suspend fun getCommentsCount(postId: Int): Result<Int>
}