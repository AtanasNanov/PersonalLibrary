package com.atanasnanov.personallibrary.ui.details

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.atanasnanov.personallibrary.data.local.Book

// Icons
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsScreen(
    book: Book,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val context = LocalContext.current

    fun shareBook() {
        val text = buildString {
            append("Check out this book:\n")
            append("📚 ${book.title}\n")
            book.author?.let { append("👤 Author: $it\n") }
            book.year?.let { append("📅 Year: $it\n") }
            book.description?.let { append("\n$it\n") }
        }

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }

        context.startActivity(Intent.createChooser(intent, "Share book"))
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(book.title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { shareBook() }) {
                        Icon(Icons.Filled.Share, contentDescription = "Share")
                    }
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Filled.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Title: ${book.title}", style = MaterialTheme.typography.titleMedium)

            book.author?.let {
                Text("Author: $it", style = MaterialTheme.typography.bodyLarge)
            }

            book.year?.let {
                Text("Year: $it", style = MaterialTheme.typography.bodyLarge)
            }

            book.description?.let {
                Text("Description:\n$it", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
