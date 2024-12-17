package com.demo.ticktick.todo.presentation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

object TodoEventBus {
    private val _events = MutableSharedFlow<TodoEvent>()
    val events: SharedFlow<TodoEvent> = _events

    suspend fun sendEvent(event: TodoEvent) {
        _events.emit(event)
    }
}

sealed class TodoEvent {
    data class TodoAdded(val message: String) : TodoEvent()
    data class TodoError(val message: String) : TodoEvent()
}