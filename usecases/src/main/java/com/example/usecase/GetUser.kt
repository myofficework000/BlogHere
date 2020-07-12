package com.example.usecase

import com.example.data.JsonPlaceHolderRepository
import com.example.domain.User

class GetUser(private val jsonApiRepository: JsonPlaceHolderRepository) {

    suspend fun invoke(id: Int): User {
        return jsonApiRepository.findUserById(id)
    }
}
