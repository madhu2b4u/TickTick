package com.demo.ticktick.todo.domain

import com.demo.ticktick.core.database.TodoEntity

interface CreateTodoUseCase {
    suspend fun createTodo(task: String): TodoEntity
}