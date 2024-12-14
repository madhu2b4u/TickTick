package com.demo.ticktick.todo.data.models

sealed class TodoDetailsUiState {
    data object Initial : TodoDetailsUiState()
    data object Loading : TodoDetailsUiState()
    data object Success : TodoDetailsUiState()
    data object Error : TodoDetailsUiState()
}