package com.demo.ticktick.todo.domain

import com.demo.ticktick.todo.data.repository.TodoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllTodoUseCaseImpl @Inject constructor(private val repository: TodoRepository) :
    GetAllTodosUseCase {
    override suspend fun getAllTodos() = repository.getAllTodos()
}