package com.example.abhishekpathak

import com.example.data.LocalDataSource
import com.example.data.RemoteDataSource
import com.example.domain.Post
import com.example.domain.User
import com.example.abhishekpathak.di.dataModule
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun initMockedDi(vararg modules: Module) {
    startKoin {
        modules(listOf(mockedAppModule, dataModule) + modules)
    }
}

private val mockedAppModule = module {
    single(named("apiKey")) { "12456" }
    single<LocalDataSource> { FakeLocalDataSource() }
    single<RemoteDataSource> { FakeRemoteDataSource() }
    single { Dispatchers.Unconfined }
}

val defaultFakePosts= listOf(
    mockedPost.copy(1),
    mockedPost.copy(2),
    mockedPost.copy(3),
    mockedPost.copy(4)
)

class FakeLocalDataSource : LocalDataSource {

    var posts: List<Post> = emptyList()

    override suspend fun isEmpty() = posts.isEmpty()

    override suspend fun savePosts(posts: List<Post>) {
        this.posts = posts
    }

    override suspend fun getPosts(): MutableList<Post> = posts as MutableList<Post>

    override suspend fun findById(id: Int): Post = posts.first { it.postId == id }

    override suspend fun update(post: Post) {
        posts = posts.filterNot { it.postId == post.postId } + post
    }

    override suspend fun isUserListEmpty(): Boolean {
        return false
    }

    override suspend fun saveUsers(users: List<User>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getUsers(): MutableList<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findUserById(id: Int): User {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getFavoritePosts(): List<Post> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updateReadStatus(id: Int, wasRead: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteAll() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deletePostById(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class FakeRemoteDataSource : RemoteDataSource {

    var posts = defaultFakePosts

    override suspend fun getPosts(): List<Post> = posts

    override suspend fun getUsers(): List<User> {
        return emptyList()
    }
}