package com.demo.ticktick.todo

import com.demo.ticktick.core.database.TodoEntity
import com.demo.ticktick.todo.data.repository.TodoRepository
import com.demo.ticktick.todo.domain.GetAllTodoUseCaseImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.jupiter.api.assertThrows

class GetAllTodosUseCaseImplTest {
    private val mockRepository: TodoRepository = mockk()
    private val getAllTodosUseCase = GetAllTodoUseCaseImpl(mockRepository)

    @Test
    fun `getAllTodos should return flow of todos when repository has todos`() = runTest {
        // Arrange
        val expectedTodos = listOf(
            TodoEntity(1, "Task 1"),
            TodoEntity(2, "Task 2")
        )

        coEvery { mockRepository.getAllTodos() } returns flowOf(expectedTodos)

        // Act
        val resultFlow = getAllTodosUseCase.getAllTodos()

        // Assert
        resultFlow.collect { todos ->
            assertEquals(expectedTodos, todos)
            assertEquals(2, todos.size)
        }
    }

    @Test
    fun `getAllTodos should return empty flow when no todos exist`() = runTest {
        // Arrange
        val expectedTodos = emptyList<TodoEntity>()

        coEvery { mockRepository.getAllTodos() } returns flowOf(expectedTodos)

        // Act
        val resultFlow = getAllTodosUseCase.getAllTodos()

        // Assert
        resultFlow.collect { todos ->
            assertTrue(todos.isEmpty())
        }
    }

    @Test
    fun `getAllTodos should propagate repository exceptions`() = runTest {
        // Arrange
        coEvery { mockRepository.getAllTodos() } throws RuntimeException("Database error")

        // Act & Assert
        val exception = assertThrows<RuntimeException> {
            getAllTodosUseCase.getAllTodos()
        }

        assertEquals("Database error", exception.message)
    }
}