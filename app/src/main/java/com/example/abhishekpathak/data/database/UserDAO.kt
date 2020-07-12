package com.example.abhishekpathak.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDAO {

    @Query("SELECT * FROM User")
    fun getAllUser(): MutableList<User>

    @Query("SELECT * FROM User WHERE userId = :userId")
    fun findUserById(userId: Int): User

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(users: List<User>)

    @Query("SELECT COUNT(userId) FROM User")
    fun userCount(): Int

    @Update
    fun updateUser(user: User)
}