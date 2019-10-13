package com.boonapps.feed.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.boonapps.feed.data.Comment
import com.boonapps.feed.data.Post
import com.boonapps.feed.data.User

@Dao
interface CommentDao {

    /**
     * Select a comment by id.
     *
     * @param id the post id.
     * @return the post with post.
     */
    @Query("SELECT COUNT(id)  FROM comments WHERE postId = :postId")
    suspend fun getCommentsCount(postId: Int): Int


    /**
     * Insert a comments in the database. If a comment already exists, replace it.
     *
     * @param post the post to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(comments: List<Comment>)
}