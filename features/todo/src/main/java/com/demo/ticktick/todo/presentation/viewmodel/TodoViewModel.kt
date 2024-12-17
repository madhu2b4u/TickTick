package com.demo.ticktick.todo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.ticktick.core.database.TodoEntity
import com.demo.ticktick.todo.domain.GetAllTodosUseCase
import com.demo.ticktick.todo.presentation.TodoEvent
import com.demo.ticktick.todo.presentation.TodoEventBus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getAllTodosUseCase: GetAllTodosUseCase,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _todos = MutableStateFlow<List<TodoEntity>>(emptyList())
    val todos: StateFlow<List<TodoEntity>> = _todos.asStateFlow()

    private val _noResultsMessage = MutableStateFlow<String?>(null)
    val noResultsMessage: StateFlow<String?> = _noResultsMessage.asStateFlow()

    private val _event = MutableStateFlow<TodoEvent?>(null)
    val event: StateFlow<TodoEvent?> = _event

    /*val filteredTodos: StateFlow<List<TodoEntity>> = combine(
        _todos,
        _searchQuery

    ) { todos, query ->
        val filtered = todos.filter {
            it.task.contains(query, ignoreCase = true)
        }
        if (filtered.isEmpty() && query.isNotEmpty()) {
            _noResultsMessage.value = "No Results"
        } else {
            _noResultsMessage.value = null
        }
        filtered

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )*/

    @OptIn(FlowPreview::class)
    val filteredTodos: StateFlow<List<TodoEntity>> = combine(
        _todos,
        _searchQuery.debounce(2000L)
    ) { todos, query ->

        val filteredList = if (query.isBlank()) {
            todos
        } else {
            todos.filter { it.task.contains(query, ignoreCase = true) }
        }

        _noResultsMessage.value =
            if (filteredList.isEmpty() && query.isNotEmpty()) {
                "No Results"
            } else {
                null
            }

        filteredList
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        fetchTodos()
        showToast()
    }

    private fun showToast() {
        viewModelScope.launch {
            TodoEventBus.events.collect { event ->
                _event.value = event
            }
        }
    }

    fun clearEvent() {
        _event.value = null
    }

    private fun fetchTodos() {
        viewModelScope.launch {
            try {
                getAllTodosUseCase.getAllTodos().collect { todoList ->
                    _todos.value = todoList
                }
            } catch (e: Exception) {
                _todos.value = emptyList()
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}
