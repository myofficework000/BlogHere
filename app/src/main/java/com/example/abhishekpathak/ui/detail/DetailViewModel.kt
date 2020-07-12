package com.example.abhishekpathak.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abhishekpathak.ui.utils.ScopedViewModel
import com.example.domain.Post
import com.example.domain.User
import com.example.usecase.CheckReadStatus
import com.example.usecase.FindPostById
import com.example.usecase.TogglePostFavorite
import com.example.usecase.UpdateUserDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class DetailViewModel(
    private val postId: Int,
    private val findPostById: FindPostById,
    private val updateUserDetails: UpdateUserDetails,
    private val togglePostFavorite: TogglePostFavorite,
    private val checkReadStatus: CheckReadStatus,
    override val uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    class UiModel(val post: Post)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) findPost()
            return _model
        }

    private fun findPost() = launch {
        _model.value = UiModel(findPostById.invoke(postId))
    }

    fun onFavoriteClicked() = launch {
        _model.value?.post?.let {
            _model.value = UiModel(togglePostFavorite.invoke(it))
        }
    }

    fun updateUserDetails(user: User) {
        launch {
            updateUserDetails.invoke(user)
        }
    }

    fun updateReadStatus() = launch {
        _model.value?.post?.let {
            _model.value = UiModel(checkReadStatus.invoke(it))
        }
    }
}