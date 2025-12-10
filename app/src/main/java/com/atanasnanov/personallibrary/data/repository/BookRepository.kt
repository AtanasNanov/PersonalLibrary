package com.atanasnanov.personallibrary.data.repository

import com.atanasnanov.personallibrary.data.local.Book
import com.atanasnanov.personallibrary.data.local.BookDao

class BookRepository(private val dao: BookDao) {

    val books = dao.getAllBooks()

    suspend fun addBook(book: Book) {
        dao.insertBook(book)
    }

    suspend fun deleteBook(book: Book) {
        dao.deleteBook(book)
    }

    suspend fun updateBook(book: Book) {
        dao.updateBook(book)
    }

}
