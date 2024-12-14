package com.demo.ticktick.todo.presentation.nav

import TodoListScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.demo.ticktick.todo.presentation.screens.TodoDetailsScreen

const val ROUTE_TODO_SCREEN = "todo_screen"
const val ROUTE_ADD_TODO_SCREEN = "add_todo_screen"


fun NavGraphBuilder.todoNavGraph(navController: NavController) {
    composable(ROUTE_TODO_SCREEN) {
        TodoListScreen(navController)
    }
    composable(ROUTE_ADD_TODO_SCREEN) {
        TodoDetailsScreen(navController)
    }
}