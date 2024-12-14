package com.demo.ticktick.todo.di

import com.demo.ticktick.todo.data.source.TodoDataSource
import com.demo.ticktick.todo.data.source.TodoDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module(includes = [TodoLocalModule.Binders::class])
@InstallIn(SingletonComponent::class)
class TodoLocalModule {
    @Module
    @InstallIn(SingletonComponent::class)
    interface Binders {
        @Binds
        fun bindsLocalDataSource(
            localDataSourceImpl: TodoDataSourceImpl
        ): TodoDataSource
    }
}

