package com.demo.ticktick.todo.domain

import com.demo.ticktick.core.database.TodoEntity
import com.demo.ticktick.todo.data.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

class GetAllTodosUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(): Flow<List<TodoEntity>> =
        repository.getAllTodos()
}

class GetTodosByStatusUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(status: Boolean): Flow<List<TodoEntity>> = 
        repository.getTodosByStatus(status)
}

class CreateTodoUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(title: String, description: String): TodoEntity = 
        repository.createTodo(title, description)
}

class UpdateTodoStatusUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(todo: TodoEntity, newStatus: Boolean): TodoEntity = 
        repository.updateTodoStatus(todo, newStatus)
}

class DeleteTodoUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(todo: TodoEntity) = 
        repository.deleteTodo(todo)
}
