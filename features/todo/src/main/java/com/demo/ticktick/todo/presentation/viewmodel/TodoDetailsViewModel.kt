package com.demo.ticktick.todo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.demo.ticktick.todo.data.models.ToastType
import com.demo.ticktick.todo.data.models.TodoDetailsUiState
import com.demo.ticktick.todo.domain.CreateTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoDetailsViewModel @Inject constructor(
    private val createTodoUseCase: CreateTodoUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<TodoDetailsUiState>(TodoDetailsUiState.Initial)
    val uiState = _uiState.asStateFlow()

    private val _toastEvent = MutableSharedFlow<Pair<String, ToastType>>()
    val toastEvent = _toastEvent.asSharedFlow()

    fun addTodo(task: String) {
        CoroutineScope(Dispatchers.IO).launch {
            if (task.equals("error", ignoreCase = true)){
                _uiState.value = TodoDetailsUiState.Error
                _toastEvent.emit(Pair("Failed to add TODO", ToastType.ERROR))
                return@launch
            }
            _uiState.value = TodoDetailsUiState.Loading
            try {
                delay(3000)
                createTodoUseCase.createTodo(task = task)
                _uiState.value = TodoDetailsUiState.Success
                _toastEvent.emit(Pair("Task added successfully!", ToastType.SUCCESS))
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = TodoDetailsUiState.Error
                _toastEvent.emit(Pair("Failed to add TODO", ToastType.ERROR))
            }
        }
    }
}