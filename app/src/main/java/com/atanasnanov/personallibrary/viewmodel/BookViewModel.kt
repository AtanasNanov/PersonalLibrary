package com.atanasnanov.personallibrary.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atanasnanov.personallibrary.data.local.Book
import com.atanasnanov.personallibrary.data.repository.BookRepository
import kotlinx.coroutines.launch

class BookViewModel(private val repository: BookRepository) : ViewModel() {

    val books = repository.books

    fun addBook(book: Book) {
        viewModelScope.launch {
            repository.addBook(book)
        }
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch {
            repository.deleteBook(book)
        }
    }

    fun updateBook(book: Book) {
        viewModelScope.launch {
            repository.updateBook(book)
        }
    }
    private val _googleResult = MutableStateFlow<Book?>(null)
    val googleResult = _googleResult

    fun searchGoogleBook(isbn: String) {
        viewModelScope.launch {
            val result = repository.fetchBookFromGoogle(isbn)
            _googleResult.value = result
        }
    }

}
