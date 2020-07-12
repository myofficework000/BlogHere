package com.example.usecase


import com.example.data.JsonPlaceHolderRepository
import com.example.domain.User

class UpdateUserDetails(private val jsonApiRepository: JsonPlaceHolderRepository) {

    suspend fun invoke(user: User) {
        jsonApiRepository.updateUser(user)
    }
}