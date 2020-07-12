package com.example.abhishekpathak.data.database

import com.example.data.LocalDataSource
import com.example.domain.Comment
import com.example.domain.Post
import com.example.domain.User
import com.example.abhishekpathak.data.toDomainPost
import com.example.abhishekpathak.data.toDomainUser
import com.example.abhishekpathak.data.toRoomPost
import com.example.abhishekpathak.data.toRoomUser
import com.example.abhishekpathak.data.toRoomComment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource(db: PostDatabase) : LocalDataSource {

    private val postDao = db.postDao()
    private val userDao = db.userDao()
    private val commentDao = db.commentDao()

    override suspend fun isEmpty(): Boolean =
        withContext(Dispatchers.IO) { postDao.postCount() <= 0 }

    override suspend fun isUserListEmpty(): Boolean =
        withContext(Dispatchers.IO) { userDao.userCount() <= 0 }

    override suspend fun savePosts(posts: List<Post>) {
        withContext(Dispatchers.IO) { postDao.insertPosts(posts.map { it.toRoomPost() }) }
    }

    override suspend fun getPosts(): MutableList<Post> = withContext(Dispatchers.IO) {
        postDao.getAll().map { it.toDomainPost() } as MutableList<Post>
    }

    override suspend fun getFavoritePosts(): List<Post> = withContext(Dispatchers.IO) {
        postDao.getAllFavorites().map { it.toDomainPost() }
    }

    override suspend fun saveUsers(users: List<User>) {
        withContext(Dispatchers.IO) { userDao.insertAll(users.map { it.toRoomUser() }) }
    }

    override suspend fun getUsers(): MutableList<User> = withContext(Dispatchers.IO) {
        userDao.getAllUser().map { it.toDomainUser() } as MutableList<User>
    }

    override suspend fun findUserById(id: Int): User = withContext(Dispatchers.IO) {
        userDao.findUserById(id).toDomainUser()
    }

    override suspend fun findById(id: Int): Post = withContext(Dispatchers.IO) {
        postDao.findById(id).toDomainPost()
    }

    override suspend fun update(post: Post) {
        withContext(Dispatchers.IO) { postDao.updatePost(post.toRoomPost()) }
    }


    override suspend fun updateUser(user: User) {
        withContext(Dispatchers.IO) { userDao.updateUser(user.toRoomUser()) }
    }


    override suspend fun updateReadStatus(id: Int, wasRead: Boolean) {
        withContext(Dispatchers.IO) { postDao.updateReadStatus(id, wasRead) }
    }

    override suspend fun deleteAll() {
        withContext(Dispatchers.IO) { postDao.deleteAll() }
    }

    override suspend fun deletePostById(id: Int) {
        withContext(Dispatchers.IO) { postDao.deleteByIdPost(id) }
    }

    override suspend fun getComments(id:Int): MutableList<Comment> = withContext(Dispatchers.IO) {
        commentDao.getForPost(id).map { it } as MutableList<Comment>
    }

    override suspend fun saveComment(comment: Comment) {
        withContext(Dispatchers.IO) { commentDao.upsert(comment.toRoomComment()) }
    }

    override suspend fun saveComments(comments: List<Comment>) {
        withContext(Dispatchers.IO) { commentDao.upsertAll(comments.map { it.toRoomComment() }) }
    }

    override suspend fun addNewPost(post: Post) {
        withContext(Dispatchers.IO) {
            postDao.insertPost(post.toRoomPost())}
    }
}