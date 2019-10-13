package com.boonapps.feed.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.boonapps.feed.data.Post


@Dao
interface PostDao {

    /**
     * Select all post from the posts table.
     *
     * @return all posts.
     */
    @Query("SELECT * FROM Posts ORDER BY id ASC")
    suspend fun getPosts(): List<Post>


    /**
     * Select a post by id.
     *
     * @param postId the post id.
     * @return the post with post.
     */
    @Query("SELECT * FROM Posts WHERE id = :postId")
    suspend fun getPostById(postId: Int): Post?


    /**
     * Insert a post in the database. If the post already exists, replace it.
     *
     * @param post the post to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: Post)

}