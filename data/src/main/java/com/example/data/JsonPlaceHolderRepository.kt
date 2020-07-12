package com.example.data

import com.example.domain.Post
import com.example.domain.User

class JsonPlaceHolderRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getAllPosts(): MutableList<Post> {
        if (localDataSource.isEmpty()) {
            val posts =
                remoteDataSource.getPosts()
            localDataSource.savePosts(posts)
        }

        return localDataSource.getPosts()
    }

    suspend fun getAllUsers(): MutableList<User> {
        if (localDataSource.isUserListEmpty()) {
            val users =
                remoteDataSource.getUsers()
            localDataSource.saveUsers(users)
        }

        return localDataSource.getUsers()
    }

    suspend fun getFavoritePosts(): List<Post> {
        if (localDataSource.isEmpty()) {
            val posts =
                remoteDataSource.getPosts()
            localDataSource.savePosts(posts)
        }

        return localDataSource.getFavoritePosts()
    }

    suspend fun findById(id: Int): Post = localDataSource.findById(id)
    suspend fun findUserById(id: Int): User = localDataSource.findUserById(id)
    suspend fun update(post: Post) = localDataSource.update(post)
    suspend fun addNewPost(post: Post) = localDataSource.addNewPost(post)
    suspend fun updateReadStatus(id: Int, wasRead: Boolean) = localDataSource.updateReadStatus(id, wasRead)
    suspend fun deleteAll() = localDataSource.deleteAll()
    suspend fun deletePostById(id: Int) = localDataSource.deletePostById(id)
    suspend fun updateUser(user: User) = localDataSource.updateUser(user)

}