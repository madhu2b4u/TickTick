package com.demo.ticktick.todo.domain

import com.demo.ticktick.core.database.TodoEntity
import kotlinx.coroutines.flow.Flow

interface GetAllTodosUseCase {
    suspend fun getAllTodos(): Flow<List<TodoEntity>>
}