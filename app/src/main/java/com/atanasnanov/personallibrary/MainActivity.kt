package com.atanasnanov.personallibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.atanasnanov.personallibrary.data.local.AppDatabase
import com.atanasnanov.personallibrary.data.repository.BookRepository
import com.atanasnanov.personallibrary.ui.booklist.BookListScreen
import com.atanasnanov.personallibrary.ui.details.BookDetailsScreen
import com.atanasnanov.personallibrary.ui.edit.AddBookScreen
import com.atanasnanov.personallibrary.ui.edit.EditBookScreen
import com.atanasnanov.personallibrary.ui.theme.PersonalLibraryTheme
import com.atanasnanov.personallibrary.viewmodel.BookViewModel
import com.atanasnanov.personallibrary.viewmodel.BookViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PersonalLibraryTheme {
                LibraryApp()
            }
        }
    }
}

@Composable
fun LibraryApp() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val database = AppDatabase.getDatabase(context)
    val repository = BookRepository(database.bookDao())

    val viewModel: BookViewModel = viewModel(
        factory = BookViewModelFactory(repository)
    )

    val books = viewModel.books.collectAsState(initial = emptyList())

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {
        composable("list") {
            BookListScreen(
                books = books.value,
                onAddBook = { navController.navigate("add") },
                onScanBook = {},
                onBookClick = { book ->
                    navController.navigate("details/${book.id}")
                }
            )
        }

        composable("add") {
            AddBookScreen(
                onSave = { book ->
                    viewModel.addBook(book)
                    navController.popBackStack()
                },
                onCancel = { navController.popBackStack() }
            )
        }

        composable("details/{bookId}") { entry ->
            val id = entry.arguments?.getString("bookId")?.toIntOrNull() ?: -1
            val book = books.value.firstOrNull { it.id == id }

            if (book != null) {
                BookDetailsScreen(
                    book = book,
                    onBack = { navController.popBackStack() },
                    onEdit = { navController.navigate("edit/${book.id}") },
                    onDelete = {
                        viewModel.deleteBook(book)
                        navController.popBackStack()
                    }
                )
            }
        }

        composable("edit/{bookId}") { entry ->
            val id = entry.arguments?.getString("bookId")?.toIntOrNull() ?: -1
            val book = books.value.firstOrNull { it.id == id }

            if (book != null) {
                EditBookScreen(
                    book = book,
                    onSave = { updated ->
                        viewModel.updateBook(updated)
                        navController.popBackStack()
                    },
                    onCancel = { navController.popBackStack() }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    PersonalLibraryTheme {
        BookListScreen(
            books = emptyList(),
            onAddBook = {},
            onScanBook = {},
            onBookClick = {}
        )
    }
}
