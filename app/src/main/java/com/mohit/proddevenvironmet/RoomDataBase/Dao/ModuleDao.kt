package com.mohit.proddevenvironmet.RoomDataBase.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mohit.proddevenvironmet.RoomDataBase.Table.ModuleEntity

@Dao
interface ModuleDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertmodule(module: List<ModuleEntity>)

    @Query("DELETE FROM module_table")
    suspend fun deleteallmodule()

    @Query("SELECT * FROM module_table")
    fun getallmodule() : LiveData<List<ModuleEntity>>

    @Query("SELECT * FROM module_table WHERE id = :moduleid")
    suspend fun getUserById(moduleid: String): ModuleEntity?

    @Query("SELECT * FROM module_table")
    suspend fun getallmoduleonce(): List<ModuleEntity>

}