package com.example.abhishekpathak.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.Post
import com.example.abhishekpathak.ui.utils.ScopedViewModel
import com.example.usecase.GetPosts
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val getPopularAnimes: GetPosts,
    uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    private val uiModel = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (uiModel.value == null) refresh()
            return uiModel
        }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val posts: List<Post>) : UiModel()
        class Navigation(val post: Post) : UiModel()
        object showUi : UiModel()
    }

    init {
        initScope()
    }

    private fun refresh() {
        uiModel.value = UiModel.showUi
    }

    fun showUi() {
        launch {
            uiModel.value = UiModel.Loading
            uiModel.value =
                UiModel.Content(getPopularAnimes.invokeFavorite())
        }
    }

    fun onAnimeClicked(post: Post) {
        uiModel.value = UiModel.Navigation(post)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}