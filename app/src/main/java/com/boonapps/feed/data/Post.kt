package com.boonapps.feed.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey


@Entity(
    tableName = "posts",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onUpdate = CASCADE,
        deferred = true
)])
data class Post constructor(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)