package com.atanasnanov.personallibrary.viewmodel

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
}
