package com.mohit.proddevenvironmet.RoomDataBase.Table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String,
    val statename : String
)