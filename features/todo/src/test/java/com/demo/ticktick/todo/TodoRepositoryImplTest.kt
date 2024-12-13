package com.demo.ticktick.todo

import com.demo.ticktick.core.database.TodoEntity
import com.demo.ticktick.todo.data.repository.TodoRepositoryImpl
import com.demo.ticktick.todo.data.source.TodoDataSourceImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TodoRepositoryImplTest {
    @MockK
    private lateinit var localDataSource: TodoDataSourceImpl

    private lateinit var todoRepository: TodoRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        todoRepository = TodoRepositoryImpl(localDataSource)
    }

    @Test
    fun `createTodo generates new todo with correct properties`() = runTest {
        // Arrange
        val title = "New Task"
        val description = "Task Description"
        coEvery { localDataSource.saveTodo(any()) } returns Unit

        // Act
        val result = todoRepository.createTodo(title, description)

        // Assert
        assertEquals(title, result.title)
        assertEquals(description, result.description)
        assertEquals(false, result.status)
        assertNotNull(result.timestamp)
        coVerify { localDataSource.saveTodo(result) }
    }

    @Test
    fun `updateTodoStatus changes todo status`() = runTest {
        // Arrange
        val originalTodo = TodoEntity(1, "Test", "Description", false)
        coEvery { localDataSource.updateTodo(any()) } returns Unit

        // Act
        val updatedTodo = todoRepository.updateTodoStatus(originalTodo, true)

        // Assert
        assertEquals(true, updatedTodo.status)
        coVerify { localDataSource.updateTodo(updatedTodo) }
    }

    @Test
    fun `getAllTodos delegates to data source`() = runTest {
        // Arrange
        val expectedTodos = flowOf(
            listOf(
                TodoEntity(1, "Todo 1", "Description 1", false),
                TodoEntity(2, "Todo 2", "Description 2", true)
            )
        )
        coEvery { localDataSource.getAllTodos() } returns expectedTodos

        // Act
        val result = todoRepository.getAllTodos().first()

        // Assert
        assertEquals(expectedTodos.first(), result)
    }

    @Test
    fun `getTodoById delegates to data source`() = runTest {
        // Arrange
        val expectedTodo = TodoEntity(1, "Test Todo", "Description", false)
        coEvery { localDataSource.getTodoById(1) } returns expectedTodo

        // Act
        val result = todoRepository.getTodoById(1)

        // Assert
        assertEquals(expectedTodo, result)
    }

    @Test
    fun `getTodosByStatus delegates to data source`() = runTest {
        // Arrange
        val completedTodos = flowOf(
            listOf(
                TodoEntity(1, "Completed Todo 1", "Description 1", true),
                TodoEntity(2, "Completed Todo 2", "Description 2", true)
            )
        )
        coEvery { localDataSource.getTodosByStatus(true) } returns completedTodos

        // Act
        val result = todoRepository.getTodosByStatus(true).first()

        // Assert
        assertEquals(completedTodos.first(), result)
    }

    @Test
    fun `updateTodoStatus changes todo status correctly`() = runTest {
        // Arrange
        val originalTodo = TodoEntity(1, "Test Todo", "Description", false)
        coEvery { localDataSource.updateTodo(any()) } returns Unit

        // Act
        val updatedTodo = todoRepository.updateTodoStatus(originalTodo, true)

        // Assert
        assertTrue(updatedTodo.status)
        assertEquals(originalTodo.id, updatedTodo.id)
        assertEquals(originalTodo.title, updatedTodo.title)
        assertEquals(originalTodo.description, updatedTodo.description)
    }

    @Test
    fun `deleteTodo delegates to data source`() = runTest {
        // Arrange
        val todoToDelete = TodoEntity(1, "Todo to Delete", "Description", false)
        coEvery { localDataSource.deleteTodo(todoToDelete) } returns Unit

        // Act
        todoRepository.deleteTodo(todoToDelete)

        // Assert
        coVerify { localDataSource.deleteTodo(todoToDelete) }
    }

}
