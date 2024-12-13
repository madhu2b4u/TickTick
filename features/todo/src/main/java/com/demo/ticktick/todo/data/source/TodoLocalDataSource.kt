package com.demo.ticktick.todo.data.source

import com.demo.ticktick.core.database.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoDataSource {
    suspend fun getAllTodos(): Flow<List<TodoEntity>>
    suspend fun getTodoById(id: Int): TodoEntity?
    suspend fun getTodosByStatus(status: Boolean): Flow<List<TodoEntity>>
    suspend fun saveTodo(todo: TodoEntity)
    suspend fun updateTodo(todo: TodoEntity)
    suspend fun deleteTodo(todo: TodoEntity)
}