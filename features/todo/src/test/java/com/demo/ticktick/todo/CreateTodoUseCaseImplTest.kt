package com.demo.ticktick.todo

import com.demo.ticktick.core.database.TodoEntity
import com.demo.ticktick.todo.data.repository.TodoRepository
import com.demo.ticktick.todo.domain.CreateTodoUseCaseImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.jupiter.api.assertThrows

class CreateTodoUseCaseImplTest {
    private val mockRepository: TodoRepository = mockk()
    private val createTodoUseCase = CreateTodoUseCaseImpl(mockRepository)

    @Test
    fun `createTodo should successfully create a todo when task is valid`() = runTest {
        // Arrange
        val task = "Buy groceries"
        val expectedTodo = TodoEntity(id = 1, task = task)

        coEvery { mockRepository.createTodo(task) } returns expectedTodo

        // Act
        val result = createTodoUseCase.createTodo(task)

        // Assert
        assertEquals(expectedTodo, result)
    }

    @Test
    fun `createTodo should throw exception when task is empty`() = runTest {
        // Arrange
        val emptyTask = ""

        coEvery { mockRepository.createTodo(emptyTask) } throws IllegalArgumentException("Task cannot be empty")

        // Act & Assert
        val exception = assertThrows<IllegalArgumentException> {
            createTodoUseCase.createTodo(emptyTask)
        }
        assertEquals("Task cannot be empty", exception.message)
    }

    @Test
    fun `createTodo should throw exception when repository fails`() = runTest {
        // Arrange
        val task = "Valid task"

        coEvery { mockRepository.createTodo(task) } throws RuntimeException("Database connection failed")

        // Act & Assert
        val exception = assertThrows<RuntimeException> {
            createTodoUseCase.createTodo(task)
        }
        assertEquals("Database connection failed", exception.message)
    }
}
