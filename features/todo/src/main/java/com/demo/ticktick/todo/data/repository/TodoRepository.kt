package com.demo.ticktick.todo.data.repository

import com.demo.ticktick.core.database.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun getAllTodos(): Flow<List<TodoEntity>>
    suspend fun getTodoById(id: Int): TodoEntity?
    suspend fun getTodosByStatus(status: Boolean): Flow<List<TodoEntity>>
    suspend fun createTodo(title: String, description: String): TodoEntity
    suspend fun updateTodoStatus(todo: TodoEntity, newStatus: Boolean): TodoEntity
    suspend fun deleteTodo(todo: TodoEntity)
}