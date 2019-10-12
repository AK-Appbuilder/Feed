package com.boonapps.feed.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.boonapps.feed.data.Post

@Database(entities = [Post::class], version = 1, exportSchema = false)
abstract class FeedsDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao
}