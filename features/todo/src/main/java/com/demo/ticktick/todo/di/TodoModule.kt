package com.demo.ticktick.todo.di

import com.demo.ticktick.core.database.TodoDao
import com.demo.ticktick.todo.data.repository.TodoRepository
import com.demo.ticktick.todo.data.repository.TodoRepositoryImpl
import com.demo.ticktick.todo.data.source.TodoDataSourceImpl
import com.demo.ticktick.todo.domain.CreateTodoUseCase
import com.demo.ticktick.todo.domain.DeleteTodoUseCase
import com.demo.ticktick.todo.domain.GetAllTodosUseCase
import com.demo.ticktick.todo.domain.GetTodosByStatusUseCase
import com.demo.ticktick.todo.domain.UpdateTodoStatusUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoModule {
    @Provides
    @Singleton
    fun provideTodoDataSource(todoDao: TodoDao): TodoDataSourceImpl =
        TodoDataSourceImpl(todoDao)

    @Provides
    @Singleton
    fun provideTodoRepository(dataSource: TodoDataSourceImpl): TodoRepository =
        TodoRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideGetAllTodosUseCase(repository: TodoRepository): GetAllTodosUseCase =
        GetAllTodosUseCase(repository)

    @Provides
    @Singleton
    fun provideGetTodosByStatusUseCase(repository: TodoRepository): GetTodosByStatusUseCase =
        GetTodosByStatusUseCase(repository)

    @Provides
    @Singleton
    fun provideCreateTodoUseCase(repository: TodoRepository): CreateTodoUseCase =
        CreateTodoUseCase(repository)


    @Provides
    @Singleton
    fun provideUpdateTodoStatusUseCase(repository: TodoRepository): UpdateTodoStatusUseCase =
        UpdateTodoStatusUseCase(repository)


    @Provides
    @Singleton
    fun provideDeleteTodoUseCase(repository: TodoRepository): DeleteTodoUseCase =
        DeleteTodoUseCase(repository)

}