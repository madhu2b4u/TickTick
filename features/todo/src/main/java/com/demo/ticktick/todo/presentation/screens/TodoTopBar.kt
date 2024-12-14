package com.demo.ticktick.todo.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.demo.ticktick.core.database.TodoEntity
import com.demo.ticktick.todo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoTopBar(
    title: String = stringResource(R.string.todo_list),
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    searchBarExpanded: Boolean,
    onSearchBarExpandedChange: (Boolean) -> Unit,
    todos: List<TodoEntity>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium.copy(
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp),
            textAlign = TextAlign.Start
        )

        TodoSearchBar(
            query = searchQuery,
            onQueryChange = onSearchQueryChange,
            expanded = searchBarExpanded,
            onExpandedChange = onSearchBarExpandedChange,
            todos = todos,
            modifier = Modifier
                .fillMaxWidth()

        )
    }
}
