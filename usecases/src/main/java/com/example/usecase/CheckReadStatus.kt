package com.example.usecase

import com.example.data.JsonPlaceHolderRepository
import com.example.domain.Post

class CheckReadStatus(private val jsonApiRepository: JsonPlaceHolderRepository) {
    suspend fun invoke(post: Post): Post = with(post) {
        copy(wasRead = true).also { jsonApiRepository.updateReadStatus(it.postId, it.wasRead) }
    }}