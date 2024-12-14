package com.demo.ticktick.todo.data.source

import com.demo.ticktick.core.database.TodoDao
import com.demo.ticktick.core.database.TodoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoDataSourceImpl @Inject constructor(private val todoDao: TodoDao) :
    TodoDataSource {

    override suspend fun getAllTodos(): Flow<List<TodoEntity>> =
        todoDao.getAllTodosFromDatabase()

    override suspend fun saveTodo(todo: TodoEntity) =
        todoDao.saveTodoToDatabase(todo)

}