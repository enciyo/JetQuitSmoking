package com.enciyo.data.di

import android.content.Context
import androidx.room.Room
import com.enciyo.data.AppDatabase
import com.enciyo.data.dao.converters.LocalDateTimeConvert
import com.enciyo.data.dao.converters.LocalDateTimeJsonAdapter
import com.enciyo.domain.Repository
import com.enciyo.data.repo.RepositoryImp
import com.enciyo.data.source.LocalDataSource
import com.enciyo.data.source.LocalDataSourceImp
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface DataModule {

    companion object {
        @Provides
        @Singleton
        fun provideAppDatabase(
            @ApplicationContext context: Context,
            localDateTimeConvert: LocalDateTimeConvert,
        ) =
            Room.databaseBuilder(context,
                AppDatabase::class.java,
                "app_name_v1.1")
                .addTypeConverter(LocalDateTimeConvert())
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
        fun providePeriodDao(db: AppDatabase) = db.periodDao()

        @Provides
        @Singleton
        fun provideMoshi(
            localDateTimeJsonAdapter: LocalDateTimeJsonAdapter,
        ): Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(localDateTimeJsonAdapter)
            .build()
    }

    @Binds
    @Singleton
    fun bindRepository(repositoryImp: RepositoryImp): com.enciyo.domain.Repository

    @Binds
    @Singleton
    fun bindRemoteDataSource(localDataSourceImp: LocalDataSourceImp): LocalDataSource

}


