package com.atanasnanov.personallibrary.ui.edit

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.atanasnanov.personallibrary.data.local.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBookScreen(
    book: Book,
    onSave: (Book) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf(book.title) }
    var author by remember { mutableStateOf(book.author ?: "") }
    var year by remember { mutableStateOf(book.year ?: "") }
    var description by remember { mutableStateOf(book.description ?: "") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Edit Book") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = author,
                onValueChange = { author = it },
                label = { Text("Author") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = { Text("Year") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val updatedBook = book.copy(
                        title = title,
                        author = author,
                        year = year,
                        description = description
                    )
                    onSave(updatedBook)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes")
            }

            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancel")
            }
        }
    }
}
