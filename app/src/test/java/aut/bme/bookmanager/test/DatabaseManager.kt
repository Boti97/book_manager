package aut.bme.bookmanager.test

import android.content.Context
import aut.bme.bookmanager.interactor.repository.BookDatabase
import aut.bme.bookmanager.model.Book

class DatabaseManager {

    private var database: BookDatabase? = null

    fun getDB(context: Context) {
        database = BookDatabase.getInstance(context)
    }

    fun getBooksInDB(): List<Book> {
        val booksInDB: ArrayList<Book> = ArrayList()
        try {
            val getBooksThread = Thread {
                booksInDB.addAll(database?.bookDAO()!!.getBooks())
            }
            getBooksThread.start()
            getBooksThread.join()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
        return booksInDB
    }

    fun insertTestBook(book: Book) {
        try {
            val insertBookThread = Thread {
                database?.bookDAO()!!.insertBooks(listOf(book))
            }
            insertBookThread.start()
            insertBookThread.join()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    fun clearDB() {
        try {
            val clearDBThread = Thread {
                database?.bookDAO()!!.deleteAll()
            }
            clearDBThread.start()
            clearDBThread.join()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    fun closeDB() {
        try {
            val closeDBThread = Thread {
                database?.close()
            }
            closeDBThread.start()
            closeDBThread.join()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
