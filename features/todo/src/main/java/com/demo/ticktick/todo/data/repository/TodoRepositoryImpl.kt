package com.demo.ticktick.todo.data.repository

import com.demo.ticktick.core.database.TodoEntity
import com.demo.ticktick.todo.data.source.TodoDataSourceImpl
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(
    private val localDataSource: TodoDataSourceImpl
) : TodoRepository {
    override suspend fun getAllTodos(): Flow<List<TodoEntity>> =
        localDataSource.getAllTodos()

    override suspend fun getTodoById(id: Int): TodoEntity? =
        localDataSource.getTodoById(id)

    override suspend fun getTodosByStatus(status: Boolean): Flow<List<TodoEntity>> =
        localDataSource.getTodosByStatus(status)

    override suspend fun createTodo(title: String, description: String): TodoEntity {
        val newTodo = TodoEntity(
            title = title,
            description = description,
            status = false  // New todos start as incomplete
        )
        localDataSource.saveTodo(newTodo)
        return newTodo
    }

    override suspend fun updateTodoStatus(todo: TodoEntity, newStatus: Boolean): TodoEntity {
        val updatedTodo = todo.copy(status = newStatus)
        localDataSource.updateTodo(updatedTodo)
        return updatedTodo
    }

    override suspend fun deleteTodo(todo: TodoEntity) =
        localDataSource.deleteTodo(todo)
}