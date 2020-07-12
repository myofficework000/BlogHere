package com.example.abhishekpathak.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post(
    @PrimaryKey(autoGenerate = true) val postId: Int,
    val userId: Int,
    val title: String,
    val body: String,
    var wasRead: Boolean,
    val favorite: Boolean
)