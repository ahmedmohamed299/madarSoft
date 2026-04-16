package com.android.madarsoft.di

import android.app.Application
import androidx.room.Room
import com.android.madarsoft.data.local.dao.UserDao
import com.android.madarsoft.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "madarsoft_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(db: AppDatabase): UserDao {
        return db.userDao
    }
}
