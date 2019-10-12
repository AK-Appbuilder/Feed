package com.boonapps.feed.source

import com.boonapps.feed.data.Post
import com.boonapps.feed.data.Result

interface FeedsRepository {

    suspend fun getPosts() : Result<List<Post>>

    suspend fun getPost(postId: Int): Result<Post>

}