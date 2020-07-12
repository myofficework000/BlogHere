package com.example.usecase

import com.example.data.JsonPlaceHolderRepository
import com.example.domain.Post

class FindPostById(private val jsonApiRepository: JsonPlaceHolderRepository) {
    suspend fun invoke(id: Int): Post = jsonApiRepository.findById(id)
}