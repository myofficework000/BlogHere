package com.example.domain

data class Post(
    val postId: Int,
    val userId: Int,
    val title: String,
    val body: String,
    var wasRead: Boolean,
    val favorite: Boolean
)