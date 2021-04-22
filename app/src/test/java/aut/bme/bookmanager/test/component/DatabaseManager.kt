package aut.bme.bookmanager.test.component

import android.content.Context
import android.util.Log
import aut.bme.bookmanager.interactor.repository.BookDatabase
import aut.bme.bookmanager.model.Book

class DatabaseManager {

    fun getBooksInDB(context: Context): List<Book> {
        val booksInDB: ArrayList<Book> = ArrayList()
        try {
            val getBooksThread = Thread {
                booksInDB.addAll(BookDatabase.getInstance(context).bookDAO().getBooks())
            }
            getBooksThread.start()
            getBooksThread.join()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
        return booksInDB
    }
}
