package aut.bme.bookmanager.test

import android.content.Context
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

    fun insertTestBook(context: Context, book: Book) {
        try {
            val insertBookThread = Thread {
                BookDatabase.getInstance(context).bookDAO().insertBooks(listOf(book))
            }
            insertBookThread.start()
            insertBookThread.join()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    fun clearDB(context: Context) {
        try {
            val clearDBThread = Thread {
                BookDatabase.getInstance(context).bookDAO().deleteAll()
            }
            clearDBThread.start()
            clearDBThread.join()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
