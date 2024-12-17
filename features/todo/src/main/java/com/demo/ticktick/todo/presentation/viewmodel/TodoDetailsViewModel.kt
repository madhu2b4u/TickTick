package com.demo.ticktick.todo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.demo.ticktick.todo.data.models.TodoDetailsUiState
import com.demo.ticktick.todo.domain.CreateTodoUseCase
import com.demo.ticktick.todo.presentation.TodoEvent
import com.demo.ticktick.todo.presentation.TodoEventBus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoDetailsViewModel @Inject constructor(
    private val createTodoUseCase: CreateTodoUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<TodoDetailsUiState>(TodoDetailsUiState.Initial)
    val uiState = _uiState.asStateFlow()

    fun addTodo(task: String) {
        CoroutineScope(Dispatchers.IO).launch {
            if (task.equals("error", ignoreCase = true)) {
                _uiState.value = TodoDetailsUiState.Error
                TodoEventBus.sendEvent(TodoEvent.TodoError("Failed to add TODO"))
                return@launch
            }
            _uiState.value = TodoDetailsUiState.Loading
            try {
                delay(3000)
                createTodoUseCase.createTodo(task = task)
                _uiState.value = TodoDetailsUiState.Success
                TodoEventBus.sendEvent(TodoEvent.TodoAdded("TODO added successfully!"))
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = TodoDetailsUiState.Error
                TodoEventBus.sendEvent(TodoEvent.TodoError("Failed to add TODO"))
            }
        }
    }
}