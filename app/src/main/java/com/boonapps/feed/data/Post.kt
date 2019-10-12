package com.boonapps.feed.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "posts")
data class Post constructor(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)