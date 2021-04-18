package aut.bme.bookmanager.interactor.repository

import androidx.room.*
import aut.bme.bookmanager.model.Book

@Dao
interface BookDAO {

    @Query("SELECT * FROM book")
    fun getBooks(): List<Book>

    @Insert
    fun insertBooks(books: List<Book>)

    @Delete
    fun deleteBook(book: Book)

    @Query("DELETE FROM book")
    fun deleteAll()

    @Update
    fun updateBook(book: Book)

}