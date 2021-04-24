package aut.bme.bookmanager.interactor.repository

import android.content.Context
import aut.bme.bookmanager.interactor.event.BookResultEvent
import aut.bme.bookmanager.model.Book
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class FavoriteBooksRepositoryInteractor @Inject constructor() : RepositoryInteractor {

    override fun getBooks(context: Context) {
        val bookResultEvent = BookResultEvent()
        val selectThread = Thread {
            bookResultEvent.books = BookDatabase.getInstance(context).bookDAO().getBooks()
        }
        selectThread.start()
        selectThread.join()
        EventBus.getDefault().post(bookResultEvent)
    }

    override fun deleteBook(context: Context, book: Book) {
        val deleteFavoriteThread = Thread {
            BookDatabase.getInstance(context).bookDAO().deleteBook(book)
        }
        deleteFavoriteThread.start()
        deleteFavoriteThread.join()
    }

    override fun insertBooks(context: Context, books: List<Book>) {
        val addFavoritesThread = Thread {
            BookDatabase.getInstance(context).runInTransaction(Runnable {
                BookDatabase.getInstance(context).bookDAO().insertBooks(books)
            })
        }
        addFavoritesThread.start()
        addFavoritesThread.join()
    }

    override fun updateBook(context: Context, book: Book) {
        val updateBookTitleThread = Thread {
            BookDatabase.getInstance(context).bookDAO().updateBook(book)
        }
        updateBookTitleThread.start()
        updateBookTitleThread.join()
    }

    override fun deleteAll(context: Context) {
        val clearDBThread = Thread {
            BookDatabase.getInstance(context).bookDAO().deleteAll()
        }
        clearDBThread.start()
        clearDBThread.join()
    }
}
