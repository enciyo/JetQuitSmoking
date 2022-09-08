package com.enciyo.data.di

import android.content.Context
import androidx.room.Room
import com.enciyo.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton
import kotlin.random.Random

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, AppDatabase::class.java, "app_name_v1.1.${UUID.randomUUID().toString()}")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideAccountDao(db: AppDatabase) = db.accountDao()

    @Provides
    @Singleton
    fun provideTaskDao(db: AppDatabase) = db.taskDao()

    @Provides
    @Singleton
    fun provideTaskPeriodsDao(db: AppDatabase) = db.taskPeriodsDao()

}


