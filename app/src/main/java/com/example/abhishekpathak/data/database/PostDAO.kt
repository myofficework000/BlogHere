package com.example.abhishekpathak.data.database

import androidx.room.*

@Dao
interface PostDAO {

    @Query("SELECT * FROM Post")
    fun getAll(): MutableList<Post>

    @Query("SELECT * FROM Post WHERE favorite = 1 ORDER BY postId ASC")
    fun getAllFavorites(): List<Post>

    @Query("SELECT * FROM Post WHERE postId = :id")
    fun findById(id: Int): Post

    @Query("SELECT COUNT(postId) FROM Post")
    fun postCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPosts(posts: List<Post>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPost(posts: Post)

    @Update
    fun updatePost(post: Post)

    @Query("UPDATE Post SET wasRead = :wasRead WHERE postId = :id")
    fun updateReadStatus(id: Int, wasRead: Boolean)

    @Query("DELETE FROM Post")
    fun deleteAll()

    @Query("DELETE FROM Post WHERE postId = :id")
    fun deleteByIdPost(id: Int)
}