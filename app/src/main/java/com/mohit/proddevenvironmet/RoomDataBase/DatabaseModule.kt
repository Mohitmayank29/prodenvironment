package com.mohit.proddevenvironmet.RoomDataBase

import android.content.Context
import androidx.room.Room
import com.mohit.proddevenvironmet.RoomDataBase.Dao.ModuleDao
import com.mohit.proddevenvironmet.RoomDataBase.Dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "MKDatabase.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao {
        return db.userDao()
    }
    @Provides
    fun provideModuleDao (db: AppDatabase): ModuleDao {
        return db.moduleDao()
    }
}