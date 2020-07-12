package com.example.abhishekpathak.data.server

import com.example.data.RemoteDataSource
import com.example.domain.Comment
import com.example.domain.Post
import com.example.domain.User
import com.example.abhishekpathak.data.toDomainPost
import com.example.abhishekpathak.data.toDomainUser

class PostDataSource : RemoteDataSource {
    override suspend fun getUsers(): List<User> =
        PostDb.service
            .getAllUsers().await()
            .map { it.toDomainUser() }

    override suspend fun getPosts(): List<Post> =
        PostDb.service
            .getAllPost().await()
            .map { it.toDomainPost() }

    override suspend fun getComments(postId: Int): List<Comment> =
        PostDb.service
            .getAllComments(postId).await()
            .map { it }
}