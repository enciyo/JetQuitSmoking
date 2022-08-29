package com.enciyo.data.di

import android.content.Context
import androidx.room.Room
import com.enciyo.data.AppDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.addAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, AppDatabase::class.java, "app_name_v1")
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


