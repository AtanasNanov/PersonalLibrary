package com.atanasnanov.personallibrary.data.local

import androidx.room.*

@Dao
interface BookDao {

    @Query("SELECT * FROM books")
    fun getAllBooks(): kotlinx.coroutines.flow.Flow<List<Book>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    @Update
    suspend fun updateBook(book: Book)   // <--- важно!
}
