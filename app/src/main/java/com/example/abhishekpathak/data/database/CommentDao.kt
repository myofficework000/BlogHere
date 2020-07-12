package com.example.abhishekpathak.data.database

import androidx.room.*

@Dao
interface CommentDao {

    @Query("SELECT * from Comment where postId = :postId")
    fun getForPost(postId: Int): MutableList<Comment>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertAll(comments: List<Comment>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(comment: Comment)

    @Delete
    fun delete(comment: Comment)
}