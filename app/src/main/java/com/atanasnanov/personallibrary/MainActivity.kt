package com.atanasnanov.personallibrary

import com.atanasnanov.personallibrary.ui.edit.AddBookScreen
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.atanasnanov.personallibrary.data.local.AppDatabase
import com.atanasnanov.personallibrary.data.repository.BookRepository
import com.atanasnanov.personallibrary.ui.booklist.BookListScreen
import com.atanasnanov.personallibrary.ui.theme.PersonalLibraryTheme
import com.atanasnanov.personallibrary.viewmodel.BookViewModel
import com.atanasnanov.personallibrary.viewmodel.BookViewModelFactory

class MainActivity : ComponentActivity() {

    lateinit var viewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize Room + Repository + ViewModel
        val database = AppDatabase.getDatabase(this)
        val repository = BookRepository(database.bookDao())

        viewModel = ViewModelProvider(
            this,
            BookViewModelFactory(repository)
        )[BookViewModel::class.java]

        setContent {
            PersonalLibraryTheme {
                LibraryApp(viewModel)
            }
        }
    }
}

@Composable
fun LibraryApp(viewModel: BookViewModel) {
    val navController = rememberNavController()
    val books = viewModel.books.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {
        composable("list") {
            BookListScreen(
                books = books.value,
                onAddBook = {
                    navController.navigate("add")
                },
                onScanBook = { /* scanner later */ },
                onBookClick = { /* details later */ }
            )
        }

        composable("add") {
            AddBookScreen(
                onSave = { book ->
                    viewModel.addBook(book)
                    navController.popBackStack()
                },
                onCancel = {
                    navController.popBackStack()
                }
            )
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
