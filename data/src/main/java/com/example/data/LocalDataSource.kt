package com.example.data

import com.example.domain.Comment
import com.example.domain.Post
import com.example.domain.User

interface LocalDataSource {
    suspend fun isEmpty(): Boolean
    suspend fun isUserListEmpty(): Boolean
    suspend fun savePosts(posts: List<Post>)
    suspend fun getPosts(): MutableList<Post>
    suspend fun saveUsers(users: List<User>)
    suspend fun getUsers(): MutableList<User>
    suspend fun findUserById(id: Int): User
    suspend fun getFavoritePosts(): List<Post>
    suspend fun findById(id: Int): Post
    suspend fun update(post: Post)
    suspend fun updateUser(user: User)
    suspend fun updateReadStatus(id: Int, wasRead: Boolean)
    suspend fun deleteAll()
    suspend fun deletePostById(id: Int)
    suspend fun getComments(id:Int): MutableList<Comment>
    suspend fun saveComments(comments: List<Comment>)
    suspend fun saveComment(comment: Comment)
    suspend fun addNewPost(post: Post)

}