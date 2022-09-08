package com.enciyo.jetquitsmoking.di

import android.app.AlarmManager
import android.content.Context
import androidx.core.content.getSystemService
import com.enciyo.data.SessionAlarmManager
import com.enciyo.jetquitsmoking.notification.SessionAlarmManagerImp
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
interface AppModule {

    companion object {
        @Provides
        @Singleton
        fun provideAlarmManager(@ApplicationContext context: Context): AlarmManager =
            context.getSystemService()!!
    }

    @Singleton
    @Binds
    fun bindSessionManager(sessionAlarmManagerImp: SessionAlarmManagerImp): SessionAlarmManager


}