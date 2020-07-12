package com.example.usecase

import com.example.data.JsonPlaceHolderRepository
import com.example.domain.Post

class AddNewPost(private val jsonApiRepository: JsonPlaceHolderRepository) {

    suspend fun invoke(post: Post) {
        jsonApiRepository.addNewPost(post)
    }

}