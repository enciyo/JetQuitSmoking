package com.enciyo.data.di

import com.enciyo.data.repo.Repository
import com.enciyo.data.repo.RepositoryImp
import com.enciyo.data.source.LocalDataSource
import com.enciyo.data.source.LocalDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface DataModule {
    @Binds
    @Singleton
    fun bindLocalDataSource(localDataSourceImp: LocalDataSourceImp): LocalDataSource

    @Binds
    @Singleton
    fun bindRepository(accountRepository: RepositoryImp): Repository
}


