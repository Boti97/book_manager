package aut.bme.bookmanager.interactor.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import aut.bme.bookmanager.model.Book

@Dao
interface BookDAO {

    @Query("SELECT * FROM book")
    fun getBooks(): List<Book>

    @Insert
    fun insertBooks(vararg book: Book)

    @Delete
    fun deleteBook(book: Book)

    @Query("DELETE FROM book")
    fun deleteAll()

}