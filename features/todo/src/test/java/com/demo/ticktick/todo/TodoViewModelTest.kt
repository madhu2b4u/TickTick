package com.demo.ticktick.todo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.demo.ticktick.core.database.TodoEntity
import com.demo.ticktick.todo.domain.GetAllTodosUseCase
import com.demo.ticktick.todo.presentation.viewmodel.TodoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class TodoViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var getAllTodosUseCase: GetAllTodosUseCase

    private lateinit var viewModel: TodoViewModel

    // Sample todo entities for testing
    private val sampleTodos = listOf(
        TodoEntity(1, "Buy groceries"),
        TodoEntity(2, "Clean house"),
        TodoEntity(3, "Prepare presentation")
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `initial state should have empty todos and search query`() = runTest {
        // Setup
        whenever(getAllTodosUseCase.getAllTodos()).thenReturn(flowOf(emptyList()))

        // Create ViewModel
        viewModel = TodoViewModel(getAllTodosUseCase)

        // Advance time to process coroutines
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify
        assertTrue(viewModel.todos.value.isEmpty())
        assertEquals("", viewModel.searchQuery.value)
    }

    @Test
    fun `fetchTodos should populate todos when successful`() = runTest {
        // Setup
        whenever(getAllTodosUseCase.getAllTodos()).thenReturn(flowOf(sampleTodos))

        // Create ViewModel
        viewModel = TodoViewModel(getAllTodosUseCase)

        // Advance time to process coroutines
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify
        assertEquals(sampleTodos, viewModel.todos.value)
    }

    @Test
    fun `fetchTodos should handle empty list`() = runTest {
        // Setup
        whenever(getAllTodosUseCase.getAllTodos()).thenReturn(flowOf(emptyList()))

        // Create ViewModel
        viewModel = TodoViewModel(getAllTodosUseCase)

        // Advance time to process coroutines
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify
        assertTrue(viewModel.todos.value.isEmpty())
    }

    @Test
    fun `fetchTodos should handle exception by setting empty list`() = runTest {
        // Setup
        whenever(getAllTodosUseCase.getAllTodos()).thenThrow(RuntimeException("Network error"))

        // Create ViewModel
        viewModel = TodoViewModel(getAllTodosUseCase)

        // Advance time to process coroutines
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify
        assertTrue(viewModel.todos.value.isEmpty())
    }

    @Test
    fun `updateSearchQuery should update search query`() = runTest {
        // Setup
        whenever(getAllTodosUseCase.getAllTodos()).thenReturn(flowOf(sampleTodos))
        viewModel = TodoViewModel(getAllTodosUseCase)

        // Act
        viewModel.updateSearchQuery("groceries")

        // Verify
        assertEquals("groceries", viewModel.searchQuery.value)
    }


    @Test
    fun `filteredTodos should return empty list when no match`() = runTest {
        // Setup
        whenever(getAllTodosUseCase.getAllTodos()).thenReturn(flowOf(sampleTodos))
        viewModel = TodoViewModel(getAllTodosUseCase)

        // Advance to populate todos
        testDispatcher.scheduler.advanceUntilIdle()

        // Act
        viewModel.updateSearchQuery("nonexistent")

        // Advance to process search
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify
        assertTrue(viewModel.filteredTodos.value.isEmpty())
    }
}