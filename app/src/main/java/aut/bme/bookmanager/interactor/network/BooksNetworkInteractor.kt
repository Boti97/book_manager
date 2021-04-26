package aut.bme.bookmanager.interactor.network

import aut.bme.bookmanager.BuildConfig.API_KEY
import aut.bme.bookmanager.interactor.event.BookResultEvent
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

/*
 * Uses BooksApi methods to conduct the remote api calls.
 */
class BooksNetworkInteractor @Inject constructor(private var booksApi: BooksApi) {

    fun getBooks(title: String) {
        val bookResultEvent = BookResultEvent()
        try {
            val booksAPICallResponse = booksApi.getBooks(title, API_KEY)

            val response = booksAPICallResponse.execute()

            if (response.code() != 200) {
                throw Exception("Result code is not 200")
            }
            bookResultEvent.code = response.code()
            bookResultEvent.books = response.body()?.books
            EventBus.getDefault().post(bookResultEvent)
        } catch (e: Exception) {
            bookResultEvent.throwable = e
            EventBus.getDefault().post(bookResultEvent)
        }
    }
}