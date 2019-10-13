package com.boonapps.feed.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "comments",
    foreignKeys = [ForeignKey(
        entity = Post::class,
        parentColumns = ["id"],
        childColumns = ["postId"],
        onUpdate = ForeignKey.CASCADE,
        deferred = true
    )])
data class Comment(
    @PrimaryKey val id: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String
)