package com.demo.ticktick.todo.data.repository

import com.demo.ticktick.core.database.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun getAllTodos(): Flow<List<TodoEntity>>
    suspend fun createTodo(title: String): TodoEntity
}