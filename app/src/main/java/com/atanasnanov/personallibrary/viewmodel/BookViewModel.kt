package com.atanasnanov.personallibrary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.atanasnanov.personallibrary.data.local.Book
import com.atanasnanov.personallibrary.data.repository.BookRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookViewModel(private val repo: BookRepository) : ViewModel() {

    val books: StateFlow<List<Book>> =
        repo.getBooks().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun addBook(book: Book) {
        viewModelScope.launch {
            repo.insert(book)
        }
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch {
            repo.delete(book)
        }
    }
}

class BookViewModelFactory(private val repo: BookRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BookViewModel(repo) as T
    }
}
