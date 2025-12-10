package com.atanasnanov.personallibrary.data.repository

import com.atanasnanov.personallibrary.data.local.Book
import com.atanasnanov.personallibrary.data.local.BookDao
import kotlinx.coroutines.flow.Flow

class BookRepository(private val dao: BookDao) {

    fun getBooks(): Flow<List<Book>> = dao.getBooks()

    suspend fun insert(book: Book) = dao.insert(book)

    suspend fun update(book: Book) = dao.update(book)

    suspend fun delete(book: Book) = dao.delete(book)
}
