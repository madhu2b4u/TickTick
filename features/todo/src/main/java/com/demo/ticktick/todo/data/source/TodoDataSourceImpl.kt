package com.demo.ticktick.todo.data.source

import com.demo.ticktick.core.database.TodoDao
import com.demo.ticktick.core.database.TodoEntity
import kotlinx.coroutines.flow.Flow

class TodoDataSourceImpl(private val todoDao: TodoDao) :
    TodoDataSource {

    override suspend fun getAllTodos(): Flow<List<TodoEntity>> =
        todoDao.getAllTodosFromDatabase()

    override suspend fun getTodoById(id: Int): TodoEntity? =
        todoDao.getTodoById(id)

    override suspend fun getTodosByStatus(status: Boolean): Flow<List<TodoEntity>> =
        todoDao.getTodosByStatus(status)

    override suspend fun saveTodo(todo: TodoEntity) =
        todoDao.saveTodoToDatabase(todo)

    override suspend fun updateTodo(todo: TodoEntity) =
        todoDao.updateTodo(todo)

    override suspend fun deleteTodo(todo: TodoEntity) =
        todoDao.deleteTodo(todo)

}