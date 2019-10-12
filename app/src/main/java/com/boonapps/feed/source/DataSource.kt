package com.boonapps.feed.source

import com.boonapps.feed.data.Post
import com.boonapps.feed.data.Result

interface DataSource {


    suspend fun getPosts() : Result<List<Post>>

    suspend fun getPost(id:Int) : Result<Post>
}