package com.example.usecase

import com.example.data.JsonPlaceHolderRepository
import com.example.domain.Post

class DeleteAllPosts(private val jsonApiRepository: JsonPlaceHolderRepository) {

    suspend fun invoke() {
        return jsonApiRepository.deleteAll()
    }
}
