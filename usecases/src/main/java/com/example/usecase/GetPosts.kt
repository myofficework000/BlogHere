package com.example.usecase

import com.example.data.JsonPlaceHolderRepository
import com.example.domain.Post
import com.example.domain.User

class GetPosts(private val jsonApiRepository: JsonPlaceHolderRepository) {

    suspend fun invoke(): MutableList<Post> {
        return jsonApiRepository.getAllPosts()
    }

    suspend fun invokeUsers(): MutableList<User> {
        return jsonApiRepository.getAllUsers()
    }

    suspend fun invokeFavorite(): List<Post> {
        return jsonApiRepository.getFavoritePosts()
    }
}
