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
    suspend fun fetchBookFromGoogle(isbn: String): Book? {
        val url = "https://www.googleapis.com/books/v1/volumes?q=isbn:$isbn"

        val connection = java.net.URL(url).openConnection() as java.net.HttpURLConnection
        connection.requestMethod = "GET"

        val response = connection.inputStream.bufferedReader().readText()

        val json = org.json.JSONObject(response)
        val items = json.optJSONArray("items") ?: return null
        val volumeInfo = items.getJSONObject(0).getJSONObject("volumeInfo")

        val title = volumeInfo.optString("title")
        val authorsArray = volumeInfo.optJSONArray("authors")
        val author = authorsArray?.optString(0)
        val description = volumeInfo.optString("description")
        val year = volumeInfo.optString("publishedDate")
        val imageLinks = volumeInfo.optJSONObject("imageLinks")
        val thumbnail = imageLinks?.optString("thumbnail")

        return Book(
            id = 0, // временно. Room ще го замести.
            title = title,
            author = author,
            description = description,
            year = year,
            coverUrl = thumbnail
        )
    }

}
