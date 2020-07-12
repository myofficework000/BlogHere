package com.example.abhishekpathak.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.abhishekpathak.ui.main.PostViewModel
import com.example.usecase.*
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PostViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getPosts: GetPosts
    @Mock
    lateinit var getUser: GetUser
    @Mock
    lateinit var deletePostById: DeletePostById
    @Mock
    lateinit var deleteAllPosts: DeleteAllPosts
    @Mock
    lateinit var checkReadStatus: CheckReadStatus

    @Mock
    lateinit var observer: Observer<PostViewModel.UiModel>

    private lateinit var viewModel: PostViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        viewModel = PostViewModel(
            getPosts,
            getUser,
            deletePostById,
            deleteAllPosts,
            checkReadStatus,
            Dispatchers.Unconfined)
    }

    @Test
    fun testLoadingIsShown() {
        runBlocking {
            viewModel.model.observeForever(observer)

            verify(observer).onChanged(PostViewModel.UiModel.showUi)
        }
    }

    @Test
    fun testGetPostsCall() {
        runBlocking {
            viewModel.model.observeForever(observer)

            verify(observer).onChanged(PostViewModel.UiModel.showUi)
        }
    }
}