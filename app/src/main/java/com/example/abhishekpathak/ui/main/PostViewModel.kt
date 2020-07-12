package com.example.abhishekpathak.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.Post
import com.example.domain.User
import com.example.abhishekpathak.ui.utils.ScopedViewModel
import com.example.usecase.AddNewPost
import com.example.usecase.CheckReadStatus
import com.example.usecase.DeleteAllPosts
import com.example.usecase.DeletePostById
import com.example.usecase.GetPosts
import com.example.usecase.GetUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class PostViewModel(
    private val getPosts: GetPosts,
    private val getUser: GetUser,
    private val deletePostById: DeletePostById,
    private val addNewPost: AddNewPost,
    private val deleteAllPosts: DeleteAllPosts,
    private val checkReadStatus: CheckReadStatus,
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
        class Content(val posts: MutableList<Post>) : UiModel()
        class UserContent(val users: MutableList<User>) : UiModel()
        class Navigation(val post: Post) : UiModel()
        class ReadStatus(val post: Post) : UiModel()
        class FindUserById(val user: User) : UiModel()
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
            uiModel.value = UiModel.Content(getPosts.invoke())
            uiModel.value = UiModel.UserContent(getPosts.invokeUsers())
        }
    }

    fun updateReadStatus(post: Post) = launch {
        uiModel.value = UiModel.ReadStatus(checkReadStatus.invoke(post))
        uiModel.value = UiModel.Content(getPosts.invoke())
    }

    fun onPostClicked(post: Post) {
        launch {
            uiModel.value = UiModel.Navigation(post)
            uiModel.value = UiModel.UserContent(getPosts.invokeUsers())
        }
    }

    fun onDeleteAllPostsClicked() {
        launch {
            deleteAllPosts.invoke()
        }
    }

    fun onDeletePostById(id: Int) {
        launch {
            deletePostById.invoke(id)
        }
    }

    fun onAddNewPost(post: Post) {
        launch {
            addNewPost.invoke(post)
        }
    }

    fun findUser(id: Int) {
        launch {
            uiModel.value = UiModel.FindUserById(getUser.invoke(id))
        }
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}