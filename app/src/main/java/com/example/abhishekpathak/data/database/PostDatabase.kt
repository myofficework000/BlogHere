package com.example.abhishekpathak.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Post::class, User::class, Comment::class], version = 1, exportSchema = false)
abstract class PostDatabase : RoomDatabase() {

    companion object {
        fun build(context: Context) = Room.databaseBuilder(
            context,
            PostDatabase::class.java,
            "post-db"
        ).build()
    }

    abstract fun postDao(): PostDAO
    abstract fun userDao(): UserDAO
    abstract fun commentDao(): CommentDao
}