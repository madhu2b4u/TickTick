import com.demo.ticktick.core.database.TodoEntity
import com.demo.ticktick.todo.domain.GetAllTodosUseCase
import com.demo.ticktick.todo.presentation.viewmodel.TodoViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TodoListViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getAllTodosUseCase: GetAllTodosUseCase
    private lateinit var viewModel: TodoViewModel

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getAllTodosUseCase = mockk()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be empty todos`() = runTest {
        // Arrange
        coEvery { getAllTodosUseCase.getAllTodos() } returns flowOf(emptyList())

        // Act
        viewModel = TodoViewModel(getAllTodosUseCase)

        // Assert
        val todos = viewModel.todos.value
        assertTrue(todos.isEmpty())
    }

    @Test
    fun `fetchTodos should update todos list`() = runTest {
        // Arrange
        val mockTodos = listOf(
            TodoEntity(1, "Task 1"),
            TodoEntity(2, "Task 2")
        )
        coEvery { getAllTodosUseCase.getAllTodos() } returns flowOf(mockTodos)

        // Act
        viewModel = TodoViewModel(getAllTodosUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val todos = viewModel.todos.value
        assertEquals(2, todos.size)
        assertEquals("Task 1", todos[0].title)
        assertEquals("Task 2", todos[1].title)
    }

    @Test
    fun `search query should filter todos correctly`() = runTest {
        // Arrange
        val mockTodos = listOf(
            TodoEntity(1, "Buy groceries"),
            TodoEntity(2, "Call mom"),
            TodoEntity(3, "Buy phone")
        )
        coEvery { getAllTodosUseCase.getAllTodos() } returns flowOf(mockTodos)

        // Act
        viewModel = TodoViewModel(getAllTodosUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.updateSearchQuery("buy")
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val filteredTodos = viewModel.filteredTodos.value
        assertEquals(2, filteredTodos.size)
        assertTrue(filteredTodos.all { it.title.contains("buy", ignoreCase = true) })
    }


    @Test
    fun `error in fetchTodos should result in empty list`() = runTest {
        // Arrange
        coEvery { getAllTodosUseCase.getAllTodos() } throws RuntimeException("Database error")

        // Act
        viewModel = TodoViewModel(getAllTodosUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val todos = viewModel.todos.value
        assertTrue(todos.isEmpty())
    }
}
