package com.boonapps.feed.source.local

import androidx.room.Dao
import androidx.room.Query
import com.boonapps.feed.data.Post


@Dao
interface PostDao {

    /**
     * Select all post from the posts table.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM Posts")
    suspend fun getPosts(): List<Post>



    /**
     * Select a task by id.
     *
     * @param taskId the task id.
     * @return the task with taskId.
     */
    @Query("SELECT * FROM Posts WHERE id = :postId")
    suspend fun getPostById(postId: Int): Post?
}