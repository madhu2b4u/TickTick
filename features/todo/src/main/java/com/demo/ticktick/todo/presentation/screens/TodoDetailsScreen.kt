package com.demo.ticktick.todo.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.demo.ticktick.todo.R
import com.demo.ticktick.todo.data.models.TodoDetailsUiState
import com.demo.ticktick.todo.presentation.viewmodel.TodoDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailsScreen(navigateToTodoScreen: () -> Unit) {

    val viewModel: TodoDetailsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    var taskText by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(uiState) {
        when (uiState) {
            TodoDetailsUiState.Success,
            TodoDetailsUiState.Error -> {
                navigateToTodoScreen()
            }

            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.add_todo)) },
                navigationIcon = {
                    IconButton(onClick = { navigateToTodoScreen() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Column {
                TextField(
                    value = taskText,
                    onValueChange = { taskText = it },
                    label = { Text(stringResource(R.string.enter_todo_task)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.addTodo(taskText)
                        keyboardController?.hide()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = taskText.isNotBlank() && uiState !is TodoDetailsUiState.Loading
                ) {
                    Text(stringResource(R.string.add_todo))
                }

                if (uiState is TodoDetailsUiState.Loading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}