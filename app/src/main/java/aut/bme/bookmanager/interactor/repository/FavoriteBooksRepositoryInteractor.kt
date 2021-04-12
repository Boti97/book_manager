package aut.bme.bookmanager.interactor.repository

import android.content.Context
import aut.bme.bookmanager.interactor.event.BookResultEvent
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class FavoriteBooksRepositoryInteractor @Inject constructor() {
    fun getBooks(context: Context) {
        val bookResultEvent = BookResultEvent()
        try {
            val selectThread = Thread {
                bookResultEvent.books = BookDatabase.getInstance(context).bookDAO().getBooks()
                EventBus.getDefault().post(bookResultEvent)
            }
            selectThread.start()
        } catch (e: Exception) {
            bookResultEvent.throwable = e
            EventBus.getDefault().post(bookResultEvent)
        }
    }
}