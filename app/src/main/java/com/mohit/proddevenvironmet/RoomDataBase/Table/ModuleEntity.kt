package com.mohit.proddevenvironmet.RoomDataBase.Table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Module_table")
data class ModuleEntity(
    @PrimaryKey
    val id : String,
    val icon : String,
    val name : String,
    val moduleurl : String
)