package com.example.abhishekpathak.data.server

import com.example.domain.Comment
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface PostDbService {

    @GET("posts")
    fun getAllPost(): Deferred<List<Post>>

    @GET("users")
    fun getAllUsers(): Deferred<List<User>>

    @GET("comments")
    fun getAllComments(@Query("postId") postId: Int): Deferred<List<Comment>>
}