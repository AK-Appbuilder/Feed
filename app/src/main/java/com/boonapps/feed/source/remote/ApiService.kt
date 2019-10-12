package com.boonapps.feed.source.remote

import com.boonapps.feed.data.Comment
import com.boonapps.feed.data.Post
import com.boonapps.feed.data.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/posts")
    suspend fun getPosts() : Response<List<Post>>

    @GET("/users")
    suspend fun getUsers() : Response<List<User>>

    @GET("/comments")
    suspend fun getComments() : Response<List<Comment>>
}