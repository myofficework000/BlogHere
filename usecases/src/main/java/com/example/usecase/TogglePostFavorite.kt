package com.example.usecase

import com.example.data.JsonPlaceHolderRepository
import com.example.domain.Post

class TogglePostFavorite(private val jsonApiRepository: JsonPlaceHolderRepository) {
    suspend fun invoke(post: Post): Post = with(post) {
        copy(favorite = !favorite).also { jsonApiRepository.update(it) }
    }
}