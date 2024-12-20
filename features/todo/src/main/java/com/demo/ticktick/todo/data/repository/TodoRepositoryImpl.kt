package com.demo.ticktick.todo.data.repository

import com.demo.ticktick.core.database.TodoEntity
import com.demo.ticktick.todo.data.source.TodoDataSourceImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoRepositoryImpl @Inject constructor(
    private val localDataSource: TodoDataSourceImpl
) : TodoRepository {
    override suspend fun getAllTodos(): Flow<List<TodoEntity>> =
        localDataSource.getAllTodos()

    override suspend fun createTodo(title: String): TodoEntity {
        val newTodo = TodoEntity(
            task = title,
        )
        localDataSource.saveTodo(newTodo)
        return newTodo
    }
}