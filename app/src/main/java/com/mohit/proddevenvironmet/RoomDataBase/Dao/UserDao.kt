package com.mohit.proddevenvironmet.RoomDataBase.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mohit.proddevenvironmet.RoomDataBase.Table.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertUser(user: List<UserEntity>)
    @Query("SELECT * FROM user_table")
    fun getAllUsers(): LiveData<List<UserEntity>>
    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()
   @Query("SELECT * FROM user_table WHERE id = :userId")
   suspend fun getUserById(userId: String): UserEntity?
   @Query("SELECT * FROM user_table")
   suspend fun getAllUsersOnce(): List<UserEntity>

}