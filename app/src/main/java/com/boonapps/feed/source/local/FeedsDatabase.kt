package com.boonapps.feed.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.boonapps.feed.data.Comment
import com.boonapps.feed.data.Post
import com.boonapps.feed.data.User

@Database(entities = [Post::class, User::class, Comment::class], version = 1, exportSchema = false)
abstract class FeedsDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao

    abstract fun userDao(): UserDao

    abstract  fun commentDao(): CommentDao
}