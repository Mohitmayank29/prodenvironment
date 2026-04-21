package com.mohit.proddevenvironmet.RoomDataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mohit.proddevenvironmet.RoomDataBase.Dao.ModuleDao
import com.mohit.proddevenvironmet.RoomDataBase.Table.ModuleEntity
import com.mohit.proddevenvironmet.RoomDataBase.Dao.UserDao
import com.mohit.proddevenvironmet.RoomDataBase.Table.UserEntity

@Database(entities = arrayOf(UserEntity::class, ModuleEntity::class), version = 4, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun moduleDao(): ModuleDao
}