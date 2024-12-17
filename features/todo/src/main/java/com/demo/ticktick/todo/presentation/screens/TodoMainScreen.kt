import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.demo.ticktick.core.database.TodoEntity
import com.demo.ticktick.todo.R
import com.demo.ticktick.todo.presentation.TodoEvent
import com.demo.ticktick.todo.presentation.screens.TodoTopBar
import com.demo.ticktick.todo.presentation.viewmodel.TodoViewModel

@Composable
fun TodoListScreen(navigateToAddTodo: () -> Unit) {
    val viewModel: TodoViewModel = hiltViewModel()
    var searchBarExpanded by rememberSaveable { mutableStateOf(false) }
    val searchQuery by viewModel.searchQuery.collectAsState()
    val todos by viewModel.filteredTodos.collectAsState()
    val noResultsMessage by viewModel.noResultsMessage.collectAsState()

    val event by viewModel.event.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    LaunchedEffect(event) {
        event?.let {
            when (it) {
                is TodoEvent.TodoAdded -> {
                    dialogMessage = it.message
                    showDialog = false
                }

                is TodoEvent.TodoError -> {
                    dialogMessage = it.message
                    showDialog = true
                }
            }
            viewModel.clearEvent()
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.ok))
                }
            },
            title = { Text(stringResource(R.string.notification)) },
            text = { Text(dialogMessage) }
        )
    }

    Scaffold(
        topBar = {
            TodoTopBar(
                searchQuery = searchQuery,
                onSearchQueryChange = { newQuery ->
                    viewModel.updateSearchQuery(newQuery)
                },
                searchBarExpanded = searchBarExpanded,
                onSearchBarExpandedChange = { searchBarExpanded = it },
                todos = todos
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToAddTodo) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_todo))
            }
        }
    ) { paddingValues ->
        TodoListContent(
            todos = todos,
            noResultsMessage = noResultsMessage,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

@Composable
fun TodoListContent(
    todos: List<TodoEntity>,
    noResultsMessage: String?,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        if (noResultsMessage != null) {
            // Show the "No results found" message
            Text(
                text = noResultsMessage,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        } else if (todos.isEmpty()) {
            Text(
                text = stringResource(R.string.press_the_button_to_add_a_todo_item),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            LazyColumn {
                items(todos) { todo ->
                    TodoItemRow(todo)
                }
            }
        }
    }
}

@Composable
fun TodoItemRow(todo: TodoEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                top = 4.dp,
                end = 16.dp,
                bottom = 4.dp
            )
    ) {
        Text(
            text = todo.task,
            modifier = Modifier.padding(16.dp)
        )
    }
}
