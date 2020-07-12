package com.example.usecase

import com.example.data.JsonPlaceHolderRepository
import com.example.domain.Post

class DeletePostById(private val jsonApiRepository: JsonPlaceHolderRepository) {

    suspend fun invoke(id: Int) {
        return jsonApiRepository.deletePostById(id)
    }
}
