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
        coEvery { localDataSource.saveTodo(any()) } returns Unit

        // Act
        val result = todoRepository.createTodo(title)

        // Assert
        assertEquals(title, result.task)
        assertNotNull(result.timestamp)
        coVerify { localDataSource.saveTodo(result) }
    }

    @Test
    fun `getAllTodos delegates to data source`() = runTest {
        // Arrange
        val expectedTodos = flowOf(
            listOf(
                TodoEntity(1, "Todo 1"),
                TodoEntity(2, "Todo 2")
            )
        )
        coEvery { localDataSource.getAllTodos() } returns expectedTodos

        // Act
        val result = todoRepository.getAllTodos().first()

        // Assert
        assertEquals(expectedTodos.first(), result)
    }

}
