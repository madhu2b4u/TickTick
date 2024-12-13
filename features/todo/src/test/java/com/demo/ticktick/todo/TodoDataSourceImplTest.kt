package com.demo.ticktick.todo

import com.demo.ticktick.core.database.TodoDao
import com.demo.ticktick.core.database.TodoEntity
import com.demo.ticktick.todo.data.source.TodoDataSourceImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class TodoDataSourceImplTest {
    @MockK
    private lateinit var todoDao: TodoDao

    private lateinit var localTodoDataSource: TodoDataSourceImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        localTodoDataSource = TodoDataSourceImpl(todoDao)
    }

    @Test
    fun `getAllTodos returns flow of todo entities`() = runTest {
        // Arrange
        val expectedTodos = listOf(
            TodoEntity(1, "Test Todo 1", "Description 1", false),
            TodoEntity(2, "Test Todo 2", "Description 2", true)
        )
        coEvery { todoDao.getAllTodosFromDatabase() } returns flowOf(expectedTodos)

        // Act
        val result = localTodoDataSource.getAllTodos().first()

        // Assert
        assertEquals(expectedTodos, result)
    }

    @Test
    fun `getAllTodos returns all todos ordered by timestamp`() = runTest {
        // Arrange
        val expectedTodos = listOf(
            TodoEntity(1, "Todo 1", "Description 1", false, 1000L),
            TodoEntity(2, "Todo 2", "Description 2", true, 2000L)
        )
        coEvery { todoDao.getAllTodosFromDatabase() } returns flowOf(expectedTodos)

        // Act
        val result = localTodoDataSource.getAllTodos().first()

        // Assert
        assertEquals(expectedTodos, result)
        coVerify { todoDao.getAllTodosFromDatabase() }
    }

    @Test
    fun `getTodoById returns specific todo when exists`() = runTest {
        // Arrange
        val expectedTodo = TodoEntity(1, "Test Todo", "Description", false)
        coEvery { todoDao.getTodoById(1) } returns expectedTodo

        // Act
        val result = localTodoDataSource.getTodoById(1)

        // Assert
        assertEquals(expectedTodo, result)
        coVerify { todoDao.getTodoById(1) }
    }

    @Test
    fun `getTodoById returns null when todo not found`() = runTest {
        // Arrange
        coEvery { todoDao.getTodoById(999) } returns null

        // Act
        val result = localTodoDataSource.getTodoById(999)

        // Assert
        assertNull(result)
        coVerify { todoDao.getTodoById(999) }
    }

    @Test
    fun `getTodosByStatus returns todos with matching status`() = runTest {
        // Arrange
        val completedTodos = listOf(
            TodoEntity(1, "Completed Todo 1", "Description 1", true),
            TodoEntity(2, "Completed Todo 2", "Description 2", true)
        )
        coEvery { todoDao.getTodosByStatus(true) } returns flowOf(completedTodos)

        // Act
        val result = localTodoDataSource.getTodosByStatus(true).first()

        // Assert
        assertEquals(completedTodos, result)
        coVerify { todoDao.getTodosByStatus(true) }
    }


    @Test
    fun `saveTodo calls dao save method`() = runTest {
        // Arrange
        val todo = TodoEntity(title = "New Todo", description = "Test Description", status = false)
        coEvery { todoDao.saveTodoToDatabase(todo) } returns Unit

        // Act
        localTodoDataSource.saveTodo(todo)

        // Assert
        coVerify { todoDao.saveTodoToDatabase(todo) }
    }

    @Test
    fun `updateTodo calls dao update method`() = runTest {
        // Arrange
        val todo = TodoEntity(1, "Updated Todo", "Updated Description", true)
        coEvery { todoDao.updateTodo(todo) } returns Unit

        // Act
        localTodoDataSource.updateTodo(todo)

        // Assert
        coVerify { todoDao.updateTodo(todo) }
    }

    @Test
    fun `deleteTodo calls dao delete method`() = runTest {
        // Arrange
        val todo = TodoEntity(1, "Todo to Delete", "Description", false)
        coEvery { todoDao.deleteTodo(todo) } returns Unit

        // Act
        localTodoDataSource.deleteTodo(todo)

        // Assert
        coVerify { todoDao.deleteTodo(todo) }
    }

}
