package com.demo.ticktick.todo.domain

import com.demo.ticktick.todo.data.repository.TodoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateTodoUseCaseImpl @Inject constructor(private val repository: TodoRepository) :
    CreateTodoUseCase {
    override suspend fun createTodo(task: String) = repository.createTodo(task)
}