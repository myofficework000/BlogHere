package com.example.abhishekpathak.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.data.LocalDataSource
import com.example.abhishekpathak.FakeLocalDataSource
import com.example.abhishekpathak.initMockedDi
import com.example.abhishekpathak.mockedPost
import com.example.abhishekpathak.ui.main.PostViewModel
import com.example.usecase.*
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PostFragmentTest : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<PostViewModel.UiModel>

    private lateinit var viewModel: PostViewModel

    @Before
    fun setup() {
        val vmModule = module {
            factory { PostViewModel(get(), get(), get(), get(), get(), get()) }
            factory { GetPosts(get()) }
            factory { GetUser(get()) }
            factory { DeletePostById(get()) }
            factory { DeleteAllPosts(get()) }
            factory { CheckReadStatus(get()) }
        }

        initMockedDi(vmModule)
        viewModel = get()
    }

    @Test
    fun testLoadDataFromServerWhenLocalIsEmpty() {
        viewModel.model.observeForever(observer)

        verify(observer).onChanged(PostViewModel.UiModel.showUi)
    }

    @Test
    fun testLocalFromDataWhenLocalIsAvailable() {
        val fakeLocalPosts = listOf(mockedPost.copy(10), mockedPost.copy(11))
        val localDataSource = get<LocalDataSource>() as FakeLocalDataSource
        localDataSource.posts = fakeLocalPosts
        viewModel.model.observeForever(observer)

        verify(observer).onChanged(PostViewModel.UiModel.showUi)
    }
}