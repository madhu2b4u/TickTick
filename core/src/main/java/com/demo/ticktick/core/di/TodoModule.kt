package com.demo.ticktick.core.di

import android.app.Application
import com.demo.ticktick.core.database.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module()
@InstallIn(SingletonComponent::class)
class TodoModule {

    @Provides
    @Singleton
    fun providesDatabase(
        application: Application
    ) = TodoDatabase.getInstance(application.applicationContext)

    @Provides
    @Singleton
    fun providesDao(
        data: TodoDatabase
    ) = data.todoDao()
}