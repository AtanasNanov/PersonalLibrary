package com.atanasnanov.personallibrary.ui.booklist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.atanasnanov.personallibrary.data.local.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    books: List<Book>,
    onAddBook: () -> Unit,
    onScanBook: () -> Unit,
    onBookClick: (Book) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My Library") }
            )
        },

        floatingActionButton = {
            Row(
                modifier = Modifier.padding(end = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Scanner button
                FloatingActionButton(
                    onClick = onScanBook,
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(Icons.Default.QrCodeScanner, contentDescription = "Scan Book")
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Add button
                FloatingActionButton(
                    onClick = onAddBook,
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Book")
                }
            }
        }
    ) { padding ->
        if (books.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No books yet.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                items(books) { book ->
                    BookListItem(
                        book = book,
                        onClick = { onBookClick(book) }
                    )
                }
            }
        }
    }
}

@Composable
fun BookListItem(book: Book, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        tonalElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleMedium
            )
            if (!book.author.isNullOrEmpty()) {
                Text(
                    text = book.author!!,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
