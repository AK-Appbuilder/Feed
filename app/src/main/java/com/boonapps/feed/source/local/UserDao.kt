package com.boonapps.feed.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.boonapps.feed.data.Post
import com.boonapps.feed.data.User

@Dao
interface UserDao {

    /**
     * Select a user by id.
     *
     * @param userId the post id.
     * @return the user.
     */
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Int): User?


    /**
     * Insert users in the database. If a user already exists, replace it.
     *
     * @param post the post to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(user: List<User>)
}