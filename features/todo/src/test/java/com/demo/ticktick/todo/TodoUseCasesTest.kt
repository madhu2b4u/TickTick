package com.demo.ticktick.todo

import com.demo.ticktick.core.database.TodoEntity
import com.demo.ticktick.todo.data.repository.TodoRepository
import com.demo.ticktick.todo.domain.CreateTodoUseCase
import com.demo.ticktick.todo.domain.DeleteTodoUseCase
import com.demo.ticktick.todo.domain.GetAllTodosUseCase
import com.demo.ticktick.todo.domain.GetTodosByStatusUseCase
import com.demo.ticktick.todo.domain.UpdateTodoStatusUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TodoUseCasesTest {
    @MockK
    private lateinit var todoRepository: TodoRepository

    private lateinit var getAllTodosUseCase: GetAllTodosUseCase
    private lateinit var getTodosByStatusUseCase: GetTodosByStatusUseCase
    private lateinit var createTodoUseCase: CreateTodoUseCase
    private lateinit var updateTodoStatusUseCase: UpdateTodoStatusUseCase
    private lateinit var deleteTodoUseCase: DeleteTodoUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getAllTodosUseCase = GetAllTodosUseCase(todoRepository)
        getTodosByStatusUseCase = GetTodosByStatusUseCase(todoRepository)
        createTodoUseCase = CreateTodoUseCase(todoRepository)
        updateTodoStatusUseCase = UpdateTodoStatusUseCase(todoRepository)
        deleteTodoUseCase = DeleteTodoUseCase(todoRepository)
    }

    @Test
    fun `CreateTodoUseCase calls repository create method`() = runTest {
        // Arrange
        val title = "New Todo"
        val description = "New Description"
        val expectedTodo = TodoEntity(title = title, description = description, status = false)
        coEvery { todoRepository.createTodo(title, description) } returns expectedTodo

        // Act
        val result = createTodoUseCase(title, description)

        // Assert
        assertEquals(expectedTodo, result)
        coVerify { todoRepository.createTodo(title, description) }
    }

    @Test
    fun `GetAllTodosUseCase invokes repository method`() = runTest {
        // Arrange
        val expectedTodos = flowOf(
            listOf(
                TodoEntity(1, "Todo 1", "Description 1", false),
                TodoEntity(2, "Todo 2", "Description 2", true)
            )
        )
        coEvery { todoRepository.getAllTodos() } returns expectedTodos

        // Act
        val result = getAllTodosUseCase().first()

        // Assert
        assertEquals(expectedTodos.first(), result)
    }

    @Test
    fun `GetTodosByStatusUseCase returns todos with specific status`() = runTest {
        // Arrange
        val completedTodos = flowOf(
            listOf(
                TodoEntity(1, "Completed Todo 1", "Description 1", true),
                TodoEntity(2, "Completed Todo 2", "Description 2", true)
            )
        )
        coEvery { todoRepository.getTodosByStatus(true) } returns completedTodos

        // Act
        val result = getTodosByStatusUseCase(true).first()

        // Assert
        assertEquals(completedTodos.first(), result)
    }

    @Test
    fun `CreateTodoUseCase creates todo with given details`() = runTest {
        // Arrange
        val title = "New Todo"
        val description = "New Description"
        val expectedTodo = TodoEntity(title = title, description = description, status = false)
        coEvery { todoRepository.createTodo(title, description) } returns expectedTodo

        // Act
        val result = createTodoUseCase(title, description)

        // Assert
        assertEquals(expectedTodo, result)
        coVerify { todoRepository.createTodo(title, description) }
    }

    @Test
    fun `UpdateTodoStatusUseCase changes todo status`() = runTest {
        // Arrange
        val originalTodo = TodoEntity(1, "Test Todo", "Description", false)
        val expectedTodo = originalTodo.copy(status = true)
        coEvery { todoRepository.updateTodoStatus(originalTodo, true) } returns expectedTodo

        // Act
        val result = updateTodoStatusUseCase(originalTodo, true)

        // Assert
        assertEquals(expectedTodo, result)
        coVerify { todoRepository.updateTodoStatus(originalTodo, true) }
    }

    @Test
    fun `DeleteTodoUseCase deletes specific todo`() = runTest {
        // Arrange
        val todoToDelete = TodoEntity(1, "Todo to Delete", "Description", false)
        coEvery { todoRepository.deleteTodo(todoToDelete) } returns Unit

        // Act
        deleteTodoUseCase(todoToDelete)

        // Assert
        coVerify { todoRepository.deleteTodo(todoToDelete) }
    }

    // Edge Case Tests
    @Test
    fun `CreateTodoUseCase handles empty title`() = runTest {
        // Arrange
        val title = ""
        val description = "Description"
        val expectedTodo = TodoEntity(title = title, description = description, status = false)
        coEvery { todoRepository.createTodo(title, description) } returns expectedTodo

        // Act
        val result = createTodoUseCase(title, description)

        // Assert
        assertEquals(title, result.title)
        assertEquals(description, result.description)
        assertFalse(result.status)
    }

    @Test
    fun `UpdateTodoStatusUseCase handles multiple status changes`() = runTest {
        // Arrange
        val originalTodo = TodoEntity(1, "Test Todo", "Description", false)
        val firstUpdate = originalTodo.copy(status = true)
        val secondUpdate = firstUpdate.copy(status = false)

        coEvery { todoRepository.updateTodoStatus(originalTodo, true) } returns firstUpdate
        coEvery { todoRepository.updateTodoStatus(firstUpdate, false) } returns secondUpdate

        // Act
        val firstResult = updateTodoStatusUseCase(originalTodo, true)
        val secondResult = updateTodoStatusUseCase(firstResult, false)

        // Assert
        assertTrue(firstResult.status)
        assertFalse(secondResult.status)
        coVerify {
            todoRepository.updateTodoStatus(originalTodo, true)
            todoRepository.updateTodoStatus(firstResult, false)
        }
    }
}
