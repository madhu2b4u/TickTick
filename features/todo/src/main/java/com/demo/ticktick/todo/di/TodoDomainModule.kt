package com.demo.ticktick.todo.di


import com.demo.ticktick.todo.data.repository.TodoRepository
import com.demo.ticktick.todo.data.repository.TodoRepositoryImpl
import com.demo.ticktick.todo.domain.CreateTodoUseCase
import com.demo.ticktick.todo.domain.CreateTodoUseCaseImpl
import com.demo.ticktick.todo.domain.GetAllTodoUseCaseImpl
import com.demo.ticktick.todo.domain.GetAllTodosUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TodoDomainModule {

    @Binds
    internal abstract fun bindRepository(
        repoImpl: TodoRepositoryImpl
    ): TodoRepository

    @Binds
    internal abstract fun bindsCreateTodoUseCase(
        useCaseImpl: CreateTodoUseCaseImpl
    ): CreateTodoUseCase

    @Binds
    internal abstract fun bindsGetTodoUseCase(
        useCaseImpl: GetAllTodoUseCaseImpl
    ): GetAllTodosUseCase

}