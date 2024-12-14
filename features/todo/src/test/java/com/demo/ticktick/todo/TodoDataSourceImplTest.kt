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
            TodoEntity(1, "Test Todo 1"),
            TodoEntity(2, "Test Todo 2")
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
            TodoEntity(1, "Todo 1", 1000L),
            TodoEntity(2, "Todo 2", 2000L)
        )
        coEvery { todoDao.getAllTodosFromDatabase() } returns flowOf(expectedTodos)

        // Act
        val result = localTodoDataSource.getAllTodos().first()

        // Assert
        assertEquals(expectedTodos, result)
        coVerify { todoDao.getAllTodosFromDatabase() }
    }
}
